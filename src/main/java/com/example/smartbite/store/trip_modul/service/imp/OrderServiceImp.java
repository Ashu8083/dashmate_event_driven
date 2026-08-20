package com.example.smartbite.store.trip_modul.service.imp;

import com.example.smartbite.store.payment_modul.model.Payment;
import com.example.smartbite.store.rider_modul.DTO.RiderDTO;
import com.example.smartbite.store.rider_modul.model.Riders;
import com.example.smartbite.store.trip_modul.DTO.*;
import com.example.smartbite.store.trip_modul.enums.OrderStatus;
import com.example.smartbite.store.trip_modul.event.TripAvailableEvent;
import com.example.smartbite.store.trip_modul.event.TripCancelAfterAssign;
import com.example.smartbite.store.trip_modul.mapper.TripMapper;
import com.example.smartbite.store.trip_modul.model.TripAssign;
import com.example.smartbite.store.trip_modul.model.TripStops;
import com.example.smartbite.store.trip_modul.model.Trips;
import com.example.smartbite.store.trip_modul.provider.CustomerProvider;
import com.example.smartbite.store.trip_modul.provider.PaymentProvider;
import com.example.smartbite.store.trip_modul.repo.TripAssignRepo;
import com.example.smartbite.store.trip_modul.repo.TripRepo;
import com.example.smartbite.store.trip_modul.repo.TripStop;
import com.example.smartbite.store.trip_modul.service.interfaces.OrderService;
import com.example.smartbite.store.user_modul.model.Users;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.UUID;

@Slf4j
@Service
public class OrderServiceImp implements OrderService {

    private final ApplicationEventPublisher applicationEventPublisher;
    private final TripRepo tripRepo;
    private final TripAssignRepo tripAssignRepo;
    private final CustomerProvider customerProvider;
    private final PaymentProvider paymentProvider;
    private final TripMapper tripMapper;
    private final TripStop tripStop;

    public OrderServiceImp(ApplicationEventPublisher applicationEventPublisher,
                           TripRepo tripRepo, TripAssignRepo tripAssignRepo,
                           CustomerProvider customerProvider, PaymentProvider paymentProvider,
                           TripMapper tripMapper, TripStop tripStop
                            ) {
        this.applicationEventPublisher = applicationEventPublisher;
        this.tripRepo = tripRepo;
        this.tripAssignRepo = tripAssignRepo;
        this.customerProvider = customerProvider;
        this.paymentProvider = paymentProvider;
        this.tripMapper = tripMapper;
        this.tripStop = tripStop;
    }

    @Transactional
    @Override
    public TripResponseDTO createTripRequest(TripRequest tripRequest) {

        Users customer = new Users();
        TripStops tripStopsPickUp = new TripStops();
        TripStops tripStopsDropOff = new TripStops();

        Trips trip = new Trips();
        customer = customerProvider.getCustomer(tripRequest.getCustomer_id());
        trip = tripMapper.createTripRequestEntity(tripRequest, customer);
        tripRepo.save(trip);
        tripStopsPickUp = tripMapper.createTripStopsPickUpRequestEntity(
                                      tripRequest.pickUpAndDropDTO.createPickUpDTO(),trip);
        tripStop.save(tripStopsPickUp);
        tripStopsDropOff = tripMapper.createTripStopDropOffRequestEntity(
                                            tripRequest.pickUpAndDropDTO.createDropDTO(),trip);
        tripStop.save(tripStopsDropOff);
        log.info("Create trip request successful");
        Payment payment  = paymentProvider.getPayment(tripRequest.getPayment_id());
        TripResponseDTO tripResponseDTO = tripMapper.createTripResponseDTOOnTripCreate(trip ,tripStopsPickUp,tripStopsDropOff);
        applicationEventPublisher.publishEvent(new TripAvailableEvent(tripRequest.package_description
                                                                     ,tripRequest.pickUpAndDropDTO,
                                                                      trip.getId(),payment,tripResponseDTO  ));
        log.info("Trip Publish Event Published ");

        return  tripResponseDTO ;
    }

    @Override
    public TripResponseDTO assignRider(RiderDTO rider, UUID tripId, TripResponseDTO tripResponseDTO) {

        Trips trip = tripRepo.findById(tripId).orElseThrow(()-> new RuntimeException("trip id not found"));
        if (trip.getStatus() != OrderStatus.PENDING){
                throw new RuntimeException("trip not available");
        }

        TripAssign tripAssign = new TripAssign();
        tripAssign.setRider_id(rider.getId());
        tripAssign.setTrip_id(trip.getId());
        tripAssign.setStatus(OrderStatus.ASSIGNED);
        trip.setTrip_assign_id(tripAssign.getId());
        trip.setStatus(OrderStatus.ASSIGNED);
        tripAssignRepo.save(tripAssign);
        tripRepo.save(trip);

        tripResponseDTO = tripMapper.updateTripResponseDTOOnTripUpdate(tripResponseDTO,rider);

        return tripResponseDTO;
    }

    @Override
    @Transactional
    public ResponseModelOnCancel cancelTripRequest(
            TripCancelRequest tripCancelRequest) {
        log.info("User requested trip cancellation. tripId={}",
                tripCancelRequest.tripId);
        Trips trip = tripRepo.findById(tripCancelRequest.tripId)
                .orElseThrow(() ->
                        new RuntimeException("Trip id not found"));
        TripAssign tripAssignPresent = tripAssignRepo.findById(trip.getTrip_assign_id())
                .orElseThrow(()->new RuntimeException("tripAssign not found"));
        OrderStatus tripStatus = trip.getStatus();
        log.info("Current trip status={} at timestamp={}",
                tripStatus, Instant.now());
        if (tripStatus == OrderStatus.PENDING) {
            trip.setStatus(OrderStatus.CANCELED);
            trip.setCancelled_at(Instant.now());
            tripRepo.save(trip);
            return new ResponseModelOnCancel();
        }
        if (tripStatus == OrderStatus.ASSIGNED) {
            trip.setStatus(OrderStatus.CANCELED);
            trip.setCancelled_at(Instant.now());
            tripAssignRepo.findById(trip.getTrip_assign_id())
                    .ifPresent(tripAssign -> {
                        tripAssign.setStatus(OrderStatus.CANCELED);
                        tripAssign.setCancelled_at(LocalDateTime.now());
                        tripAssignRepo.save(tripAssign);
                    });
            tripRepo.save(trip);
            applicationEventPublisher.publishEvent(
                    new TripCancelAfterAssign(tripAssignPresent.getRider_id())
            );
            return new ResponseModelOnCancel();
        }
        throw new RuntimeException(
                "Trip cannot be cancelled in current status: " + tripStatus
        );
    }
}

