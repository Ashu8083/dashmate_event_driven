package com.example.smartbite.store.trip_modul.service.imp;

import com.example.smartbite.store.trip_modul.DTO.ResponseModelOnCancel;
import com.example.smartbite.store.trip_modul.DTO.ResponseTripModel;
import com.example.smartbite.store.trip_modul.DTO.TripCancelRequest;
import com.example.smartbite.store.trip_modul.DTO.TripRequest;
import com.example.smartbite.store.trip_modul.event.TripAvailableEvent;
import com.example.smartbite.store.trip_modul.mapper.TripMapper;
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
import org.hibernate.annotations.Cache;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

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
    public ResponseTripModel createTripRequest(TripRequest tripRequest) {

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
        applicationEventPublisher.publishEvent(new TripAvailableEvent(tripRequest.package_description
                ,tripRequest.pickUpAndDropDTO));



        paymentProvider.checkPayment(tripRequest.getPayment_id());

        return null;
    }

    @Override
    public ResponseTripModel updateTripRequest(TripRequest tripRequestModel) {
        return null;
    }

    @Override
    public ResponseModelOnCancel cancelTripRequest(TripCancelRequest tripCancelRequest) {
        return null;
    }

    @Override
    public List<ResponseTripModel> getAllTripRequestsByUserId(UUID userId) {
        return List.of();
    }


}

