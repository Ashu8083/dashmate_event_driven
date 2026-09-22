package com.example.smartbite.store.rider_modul.internalModule.service;


import com.example.smartbite.store.common.execption.ResourceNotFoundException;
import com.example.smartbite.store.kafaka.enums.TripTypeEvent;
import com.example.smartbite.store.kafaka.events.TripAssignEvent;
import com.example.smartbite.store.kafaka.events.TripCreateEvent;
import com.example.smartbite.store.kafaka.events.TripEvent;
import com.example.smartbite.store.kafaka.producer.TripProducer;
import com.example.smartbite.store.rider_modul.internalModule.RiderSessionManager.RiderSessionAManager;
import com.example.smartbite.store.rider_modul.internalModule.model.Riders;
import com.example.smartbite.store.rider_modul.internalModule.repo.RiderRepo;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

@Slf4j
@Service
@Component
public class TripRequestService {

    private final  RiderService riderService;
    private final RiderGeoService riderGeoService;
    private  final ObjectMapper objectMapper;
    private final TripProducer tripProducer;
    private final  RiderSessionAManager riderSessionAManager;
    private final RiderRepo riderRepo;

    public  TripRequestService(RiderService riderService , TripProducer tripProducer,RiderGeoService riderGeoService,
                                RiderSessionAManager riderSessionAManager , RiderRepo riderRepo , ObjectMapper objectMapper) {
        this.riderService = riderService;
        this.riderGeoService = riderGeoService;
        this.tripProducer = tripProducer;
        this.objectMapper = objectMapper;
        this.riderSessionAManager = riderSessionAManager;
        this.riderRepo = riderRepo;

    }

    @Transactional
    public  void tripAvailableEvent (TripCreateEvent tripAvailableEvent) throws IOException {


        List<String> riderList = riderGeoService.findNearbyRiders(tripAvailableEvent.pickUpAndDropDTO().createPickUpDTO().longitude()
                                        ,tripAvailableEvent.pickUpAndDropDTO().createPickUpDTO().latitude(),2);


//        Riders rider = riderRepo.findNearestAvailableRider(tripAvailableEvent.pickUpAndDropDTO()
//                                                            .createPickUpDTO().latitude(),
//                                                            tripAvailableEvent.pickUpAndDropDTO(
//                                                            ).createPickUpDTO().longitude())
//                                .orElseThrow(()-> new ResourceNotFoundException("No rider currently available"));

        UUID riderId = UUID.fromString(riderList.get(0));
        WebSocketSession riderSession = riderSessionAManager.get(riderId);

        log.info("rider session id {}", riderSession.getId());
        riderSession.sendMessage(
                new TextMessage("New Trip Available!")

        );
        String message = """
        {
          "type": "NEW_TRIP_REQUEST",
          "tripId": "%s",
          "pickup": "%s",
          "drop_off": "%s",
          "payment": %s
        }
        """.formatted(
                tripAvailableEvent.trip_id(),
                tripAvailableEvent.pickUpAndDropDTO().createPickUpDTO().address(),
                tripAvailableEvent.pickUpAndDropDTO().createDropDTO().address(),
                tripAvailableEvent.payment()
        );
        riderSession.sendMessage(
                new TextMessage(message)
        );


    }

    @Transactional
    public void tripAccept (UUID riderId,String tripId) {


        UUID tripID = UUID.fromString(tripId);

        log.info("rider id : {}", riderId);
        Riders rider = riderRepo.findById(riderId)
                .orElseThrow(()-> new ResourceNotFoundException("No rider currently available"));

        rider.setIsAvailable(false);

        TripAssignEvent tripAssignEvent = new TripAssignEvent(
                tripID,
                rider.getId(),
                rider.getUserId(),
                rider.getAge(),
                rider.getGender()
        );

        log.info("tripAssignEvent {}", tripAssignEvent);

        JsonNode payload = objectMapper.convertValue(tripAssignEvent, JsonNode.class);

        TripEvent tripEvent = new TripEvent(TripTypeEvent.TRIP_ASSIGNED
                                            ,payload);

        tripProducer.sendtTripAssignEvent(tripEvent);

        log.info("tripAssignEvent created and called {}", tripAssignEvent);

    }
}
