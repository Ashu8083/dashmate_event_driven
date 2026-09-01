package com.example.smartbite.store.trip_modul.internalModule.service.imp;

import com.example.smartbite.store.payment_modul.internalModule.model.Payment;
import com.example.smartbite.store.rider_modul.DTO.RiderDTO;
import com.example.smartbite.store.rider_modul.DTO.RiderModelReplicaDTO;
import com.example.smartbite.store.rider_modul.publicAPi.RiderPublicAPIImpl;
import com.example.smartbite.store.trip_modul.DTO.ResponseModelOnCancel;
import com.example.smartbite.store.trip_modul.DTO.TripCancelRequest;
import com.example.smartbite.store.trip_modul.DTO.TripRequest;
import com.example.smartbite.store.trip_modul.DTO.TripResponseDTO;
import com.example.smartbite.store.trip_modul.internalModule.enums.OrderStatus;
import com.example.smartbite.store.trip_modul.internalModule.enums.StopType;
import com.example.smartbite.store.trip_modul.internalModule.event.TripAvailableEvent;
import com.example.smartbite.store.trip_modul.internalModule.event.TripCancelAfterAssign;
import com.example.smartbite.store.trip_modul.mapper.TripMapper;
import com.example.smartbite.store.trip_modul.internalModule.model.TripAssign;
import com.example.smartbite.store.trip_modul.internalModule.model.TripStops;
import com.example.smartbite.store.trip_modul.internalModule.model.Trips;
import com.example.smartbite.store.trip_modul.internalModule.provider.PaymentProvider;
import com.example.smartbite.store.trip_modul.internalModule.repo.TripAssignRepo;
import com.example.smartbite.store.trip_modul.internalModule.repo.TripRepo;
import com.example.smartbite.store.trip_modul.internalModule.repo.TripStopRepo;
import com.example.smartbite.store.trip_modul.internalModule.service.interfaces.OrderService;
import com.example.smartbite.store.user_modul.publicApi.UserModuleApi;
import com.example.smartbite.store.user_modul.publicApi.UserModuleApiImpl;
import com.example.smartbite.store.user_modul.publicApi.UserModuleReplica;
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
    private final PaymentProvider paymentProvider;
    private final UserModuleApiImpl userModuleApi;
    private final RiderPublicAPIImpl riderPublicAPI;
    private final TripMapper tripMapper;
    private final TripStopRepo tripStop;

    public OrderServiceImp(
            ApplicationEventPublisher applicationEventPublisher,
            TripRepo tripRepo, TripAssignRepo tripAssignRepo,
            UserModuleApiImpl userModuleApi , PaymentProvider paymentProvider,
            TripMapper tripMapper, TripStopRepo tripStop,
            RiderPublicAPIImpl riderPublicAPI

                            ) {
        this.applicationEventPublisher = applicationEventPublisher;
        this.tripRepo = tripRepo;
        this.tripAssignRepo = tripAssignRepo;
        this.userModuleApi = userModuleApi;
        this.riderPublicAPI = riderPublicAPI;
        this.paymentProvider = paymentProvider;
        this.tripMapper = tripMapper;
        this.tripStop = tripStop;
    }

    @Transactional
    @Override
    public TripResponseDTO createTripRequest(TripRequest tripRequest) {

        UserModuleReplica customer = userModuleApi.getUserModuleReplica(tripRequest.getCustomer_id());
        Trips trip = tripMapper.createTripRequestEntity(tripRequest, customer.user_id());
        tripRepo.save(trip);
        TripStops tripStopsPickUp = tripMapper.createTripStopsPickUpRequestEntity(
                                      tripRequest.pickUpAndDropDTO.createPickUpDTO(),trip);
        tripStop.save(tripStopsPickUp);
        TripStops tripStopsDropOff = tripMapper.createTripStopDropOffRequestEntity(
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
    public TripResponseDTO assignRider(UUID riderId, UUID tripId) {

        Trips trip = tripRepo.findById(tripId).orElseThrow(()-> new RuntimeException("trip id not found"));
        if (trip.getStatus() != OrderStatus.PENDING){
                throw new RuntimeException("trip not available");
        }

        TripAssign tripAssign = new TripAssign();
        tripAssign.setRider_id(riderId);
        tripAssign.setTrip(trip);
        tripAssign.setStatus(OrderStatus.ASSIGNED);
        RiderModelReplicaDTO riderDTO =  riderPublicAPI.getRiderReplica(riderId);
        trip.setTripAssign(tripAssign);
        trip.setStatus(OrderStatus.ASSIGNED);
        tripAssignRepo.save(tripAssign);
        tripRepo.save(trip);
        TripStops dropOff = tripStop.findByTripIdAndStopType(trip.getId(), StopType.DROP);
        TripStops pickUp = tripStop.findByTripIdAndStopType(trip.getId(), StopType.PICKUP);
        UserModuleReplica riderUser = userModuleApi.getUserModuleReplica(riderId);

        TripResponseDTO tripResponseDTO = tripMapper.createTripResponseDTOOnTripAssign(trip,
                pickUp,dropOff,riderDTO,riderUser.user_name());


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
        TripAssign tripAssignPresent = trip.getTripAssign();
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

            tripAssignPresent.setStatus(OrderStatus.CANCELED);
            tripAssignPresent.setCancelled_at(LocalDateTime.now());
            tripAssignRepo.save(tripAssignPresent);

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

