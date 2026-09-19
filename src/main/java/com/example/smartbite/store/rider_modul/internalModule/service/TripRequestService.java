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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

import java.io.IOException;
import java.util.Optional;

@Component
public class TripRequestService {

    @Autowired
    private RiderService riderService;
    private RiderProducer riderProducer;
    private RiderSessionAManager riderSessionAManager;
    private RiderRepo riderRepo;

    @Transactional
    public  void tripAvailableEvent (TripCreateEvent tripAvailableEvent) throws IOException {

        Riders rider = riderRepo.findNearestAvailableRider(tripAvailableEvent.pickUpAndDropDTO()
                                                            .createPickUpDTO().latitude(),
                                                            tripAvailableEvent.pickUpAndDropDTO(
                                                            ).createPickUpDTO().latitude())
                                .orElseThrow(()-> new ResourceNotFoundException("No rider currently available"));
        WebSocketSession riderSession = riderSessionAManager.get(rider.getId());
        rider.setIsAvailable(false);
        riderSession.sendMessage(
                new TextMessage("Trip Accepted")
        );

        TripAssignEvent tripAssignEvent = new TripAssignEvent(
                tripAvailableEvent.trip_id(),
                rider.getId(),
                rider.getUserId(),
                rider.getAge(),
                rider.getGender()
        );

        riderProducer.tripAssigned(tripAssignEvent);

    }
}
