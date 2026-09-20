package com.example.smartbite.store.rider_modul.internalModule.service;


import com.example.smartbite.store.common.execption.ResourceNotFoundException;
import com.example.smartbite.store.kafaka.events.TripAssignEvent;
import com.example.smartbite.store.kafaka.events.TripCreateEvent;
import com.example.smartbite.store.kafaka.producer.RiderProducer;
import com.example.smartbite.store.rider_modul.internalModule.RiderSessionManager.RiderSessionAManager;
import com.example.smartbite.store.rider_modul.internalModule.model.Riders;
import com.example.smartbite.store.rider_modul.internalModule.repo.RiderRepo;
import com.example.smartbite.store.trip_modul.internalModule.event.TripAvailableEvent;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

import java.io.IOException;
import java.util.Optional;
import java.util.UUID;

@Slf4j
@Service
@Component
public class TripRequestService {


    private final  RiderService riderService;
    private final  RiderProducer riderProducer;
    private final  RiderSessionAManager riderSessionAManager;
    private final RiderRepo riderRepo;

    public  TripRequestService(RiderService riderService , RiderProducer riderProducer,
                                RiderSessionAManager riderSessionAManager , RiderRepo riderRepo){
        this.riderService = riderService;
        this.riderProducer = riderProducer;
        this.riderSessionAManager = riderSessionAManager;
        this.riderRepo = riderRepo;

    }

    @Transactional
    public  void tripAvailableEvent (TripCreateEvent tripAvailableEvent) throws IOException {

        Riders rider = riderRepo.findNearestAvailableRider(tripAvailableEvent.pickUpAndDropDTO()
                                                            .createPickUpDTO().latitude(),
                                                            tripAvailableEvent.pickUpAndDropDTO(
                                                            ).createPickUpDTO().longitude())
                                .orElseThrow(()-> new ResourceNotFoundException("No rider currently available"));
        WebSocketSession riderSession = riderSessionAManager.get(rider.getId());
        log.info("rider session id {}", riderSession.getId());
        rider.setIsAvailable(false);
        riderSession.sendMessage(
                new TextMessage("New Trip Available!")

        );
        String message = """
        {
          "type": "NEW_TRIP_REQUEST",
          "tripId": "%s",
          "pickup": "%s",
          "dropoff": "%s",
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
    public void tripAccept (String tripId,String riderId) {

        UUID riderID = UUID.fromString(riderId);
        UUID tripID = UUID.fromString(tripId);

        Riders rider = riderRepo.findById(riderID).orElseThrow(()-> new ResourceNotFoundException("No rider currently available"));

        TripAssignEvent tripAssignEvent = new TripAssignEvent(
                rider.getId(),
                tripID,
                rider.getUserId(),
                rider.getAge(),
                rider.getGender()
        );

        log.info("tripAssignEvent {}", tripAssignEvent);

        riderProducer.tripAssigned(tripAssignEvent);

        log.info("tripAssignEvent created and called {}", tripAssignEvent);




    }
}
