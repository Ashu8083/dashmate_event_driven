package com.example.smartbite.store.trip_modul.internalModule.service.imp;

import com.example.smartbite.store.common.execption.ResourceNotFoundException;

import com.example.smartbite.store.kafaka.enums.TripTypeEvent;
import com.example.smartbite.store.kafaka.events.TripAssignEvent;
import com.example.smartbite.store.kafaka.events.TripCreateEvent;
import com.example.smartbite.store.kafaka.events.TripEvent;
import com.example.smartbite.store.kafaka.producer.TripProducer;
import com.example.smartbite.store.rider_modul.DTO.RiderModelReplicaDTO;
import com.example.smartbite.store.rider_modul.publicAPi.RiderPublicAPIImpl;
import com.example.smartbite.store.trip_modul.DTO.*;
import com.example.smartbite.store.trip_modul.internalModule.enums.OrderStatus;
import com.example.smartbite.store.trip_modul.internalModule.event.TripCancelAfterAssign;
import com.example.smartbite.store.trip_modul.mapper.TripMapper;
import com.example.smartbite.store.trip_modul.internalModule.model.TripAssign;
import com.example.smartbite.store.trip_modul.internalModule.model.TripStops;
import com.example.smartbite.store.trip_modul.internalModule.model.Trips;
import com.example.smartbite.store.trip_modul.internalModule.repo.TripAssignRepo;
import com.example.smartbite.store.trip_modul.internalModule.repo.TripRepo;
import com.example.smartbite.store.trip_modul.internalModule.repo.TripStopRepo;
import com.example.smartbite.store.trip_modul.internalModule.service.interfaces.OrderService;
import com.example.smartbite.store.user_modul.publicApi.UserModuleApiImpl;
import com.example.smartbite.store.user_modul.publicApi.UserModuleReplica;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
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
    private final ObjectMapper objectMapper;
    private final TripProducer tripProducer;
    private final TripAssignRepo tripAssignRepo;
    private final UserModuleApiImpl userModuleApi;
    private final RiderPublicAPIImpl riderPublicAPI;
    private final TripMapper tripMapper;
    private final TripStopRepo tripStopRepo;

    public OrderServiceImp(
            ApplicationEventPublisher applicationEventPublisher,
            TripRepo tripRepo, TripAssignRepo tripAssignRepo,
            TripStopRepo tripStopRepo,
            ObjectMapper objectMapper,
            TripProducer tripProducer,
            UserModuleApiImpl userModuleApi ,
            TripMapper tripMapper,
            RiderPublicAPIImpl riderPublicAPI
                            ) {
        this.applicationEventPublisher = applicationEventPublisher;
        this.tripRepo = tripRepo;
        this.objectMapper = objectMapper;
        this.tripAssignRepo = tripAssignRepo;
        this.tripProducer = tripProducer;
        this.userModuleApi = userModuleApi;
        this.tripStopRepo = tripStopRepo;
        this.riderPublicAPI = riderPublicAPI;
        this.tripMapper = tripMapper;
    }

    @Transactional
    @Override
    public TripResponseDTO createTripRequest(TripRequest tripRequest) {

        Trips trip  = new Trips();
        TripStops pickUpTripStop = new TripStops();
        TripStops dropOffTripStop = new TripStops();

        CreateDropDTO dropOffRequest = tripRequest.getPickUpAndDropDTO().createDropDTO();


        UserModuleReplica userModuleReplica = userModuleApi.getUserModuleReplica(tripRequest.customer_id);

        if (userModuleReplica == null) {
            throw new ResourceNotFoundException("User not found");
        }

        trip.setCustomerId(userModuleReplica.user_id());
        trip.setPackage_description(tripRequest.getPackage_description());
        trip.setScheduled_at(tripRequest.getScheduled_time());

        tripRepo.save(trip);

        pickUpTripStop.setTrip(trip);
        pickUpTripStop.setAddress(tripRequest.getPickUpAndDropDTO().createPickUpDTO().address());
        pickUpTripStop.setLatitude(tripRequest.getPickUpAndDropDTO().createPickUpDTO().latitude());
        pickUpTripStop.setLongitude(tripRequest.getPickUpAndDropDTO().createPickUpDTO().longitude());
        pickUpTripStop.setStopType(tripRequest.getPickUpAndDropDTO().createPickUpDTO().stopType());

        dropOffTripStop.setTrip(trip);
        dropOffTripStop.setAddress(dropOffRequest.address());
        dropOffTripStop.setLatitude(dropOffRequest.latitude());
        dropOffTripStop.setLongitude(dropOffRequest.longitude());
        dropOffTripStop.setStopType(dropOffRequest.stopType());

        TripStops tripPickUp =   tripStopRepo.save(pickUpTripStop);
        TripStops tripDropOff = tripStopRepo.save(dropOffTripStop);

        trip.setPickupId(tripPickUp.getId());
        trip.setDropOffId(tripDropOff.getId());
        trip.setStatus(OrderStatus.PENDING);
        tripRepo.save(trip);

        TripResponseDTO tripResponseDTO = tripMapper.createTripResponseDTOOnTripAssign(trip,tripPickUp,tripDropOff);

        TripCreateEvent tripCreateEvent = tripMapper.createTripCreateEventDTO(trip,tripRequest.pickUpAndDropDTO);

        log.info("Inside the tripCreateEvent method which is known as OderService trip producer called ");

        JsonNode jsonNode = objectMapper.convertValue(tripCreateEvent, JsonNode.class);

        TripEvent event  = new TripEvent(TripTypeEvent.TRIP_CREATED, jsonNode);

        tripProducer.sendTripCreateEvent(event);

        log.info("After create event called");

        return  tripResponseDTO ;
    }

    @Transactional
    @Override
    public TripResponseDTO assignRider(TripAssignEvent tripAssignEvent) {

        UUID userId =  tripAssignEvent.user_id();
        UUID riderId = tripAssignEvent.rider_id();
        UUID tripId = tripAssignEvent.tripId();
        log.info("InsideTripResponseDTO  assignRider service");

        Trips trip = tripRepo.findById(tripId).orElseThrow(()-> new RuntimeException("trip id not found"));
        if (trip.getStatus() != OrderStatus.PENDING){
                throw new RuntimeException("trip not available");
        }
        TripAssign tripAssign = new TripAssign();
        tripAssign.setRiderId(riderId);
        tripAssign.setTrip(trip);
        tripAssign.setStatus(OrderStatus.ASSIGNED);
        RiderModelReplicaDTO riderDTO =  riderPublicAPI.getRiderReplica(riderId);
        if (riderDTO == null ) {
            throw new ResourceNotFoundException("Rider not found");
        }
        trip.setStatus(OrderStatus.ASSIGNED);
        tripAssignRepo.saveAndFlush(tripAssign);
        trip.setTripAssign(tripAssign);
        log.info("TripAssign ID = {}", tripAssign.getId());
        tripRepo.saveAndFlush(trip);
        UserModuleReplica riderUser = userModuleApi.getUserModuleReplica(riderDTO.user_id());
//        TripResponseDTO tripResponseDTO = tripMapper.createTripResponseDTOOnTripAssign(trip,
//               riderDTO,riderUser.user_name());
        RiderAssigned riderAssignedDTO = tripMapper.createRiderAssignedDTO(riderUser.user_name()
                                                                            ,riderDTO.gander(),riderDTO.age());

        log.info("Assign trip response successful");

        return null;
    }

    @Override
    public TripResponseDTO updateOrderStatus(TripStatusUpdate tripStatusUpdate) {

        Trips trip  = tripRepo.findById(tripStatusUpdate.tripId()).orElseThrow(()-> new RuntimeException("trip id not found"));

        if(trip.getStatus() == OrderStatus.ASSIGNED && trip.getTripAssign().getRiderId().equals(tripStatusUpdate.riderID())){
            log.info("Inside the trip update method  update dtp {}", tripStatusUpdate);
            OrderStatus orderStatus = tripStatusUpdate.tripStatus() ;
            trip.setStatus(orderStatus);
            tripRepo.save(trip);
            log.info("Order status updated successful to OUR_FOR_DELEVERY");
        }
        if(trip.getStatus() == OrderStatus.OUT_FOR_DELIVERY && trip.getTripAssign().getRiderId().equals(tripStatusUpdate.riderID())){
            if (tripStatusUpdate.tripStatus() == OrderStatus.DELIVERED) {
                log.info("Inside the trip update method  update dtp {}", tripStatusUpdate);
                trip.setStatus(OrderStatus.DELIVERED);
                tripRepo.save(trip);
                riderPublicAPI.updateRiderStatus(tripStatusUpdate.riderID());
                log.info("Updated rider status to Available and OderStatus to Delevered ");
            }

        }

        return null;
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
                    new TripCancelAfterAssign(tripAssignPresent.getRiderId())
            );
            return new ResponseModelOnCancel();
        }
        throw new RuntimeException(
                "Trip cannot be cancelled in current status: " + tripStatus
        );
    }
}

