package com.example.smartbite.store.kafaka.consumer;


import com.example.smartbite.store.kafaka.events.TripAssignEvent;
import com.example.smartbite.store.kafaka.events.TripCreateEvent;
import com.example.smartbite.store.kafaka.events.TripEvent;
import com.example.smartbite.store.rider_modul.internalModule.service.TripRequestService;
import com.example.smartbite.store.trip_modul.internalModule.service.interfaces.OrderService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Slf4j
@Service
public class TripConsumer {

    final private TripRequestService tripRequestService;
    final private ObjectMapper objectMapper ;
    final private OrderService orderService;

    public TripConsumer(TripRequestService tripRequestService , ObjectMapper objectMapper , OrderService oderService) {
        this.tripRequestService = tripRequestService;
        this.orderService = oderService;
        this.objectMapper = objectMapper;
    }

    @KafkaListener(
            topics = "trip-event",
            groupId = "rider-service"
    )
    public void consumer (TripEvent event) throws IOException {
        log.info("Received trip event {}", event);

        switch (event.type()){
            case TRIP_CREATED:
                log.info("Trip created event consume");
                TripCreateEvent tripEvent = objectMapper.convertValue(event.payload(), TripCreateEvent.class);
                log.info(tripEvent.toString()+"deserilazer work");
                tripRequestService.tripAvailableEvent(tripEvent);
                break;

            case TRIP_ASSIGNED:
                log.info("Trip assigned event consume");
                TripAssignEvent tripAssignEvent = objectMapper.convertValue(event.payload(),TripAssignEvent.class);
                log.info(tripAssignEvent.toString()+"deserilazer work");
                orderService.assignRider(tripAssignEvent);
            case TRIP_UPDATED:

                log.info("Trip update event produce");
                TripCreateEvent tripEvent2 = objectMapper.convertValue(event.payload(), TripCreateEvent.class);
                log.info("Trip updated event produce{}",tripEvent2);
                break;

            case TRIP_CANCELLED :
                log.info("Trip cancelled event produce");

                break ;
            default:
        }

    }


}
