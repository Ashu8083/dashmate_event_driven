package com.example.smartbite.store.kafaka.consumer;

import com.example.smartbite.store.kafaka.events.TripAssignEvent;
import com.example.smartbite.store.trip_modul.internalModule.service.interfaces.OrderService;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Service
public class RiderConsumer {

    private final OrderService orderService;

    public RiderConsumer(OrderService orderService) {
        this.orderService = orderService;
    }

    @KafkaListener(
            topics = "trip-event",
            groupId = "trip-service"
    )
    public void listenTripAssign(TripAssignEvent tripAssignEvent) {
        orderService.assignRider(tripAssignEvent.rider_id(), tripAssignEvent.tripId());
    }

}
