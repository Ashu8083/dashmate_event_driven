package com.example.smartbite.store.kafaka.consumer;


import com.example.smartbite.store.kafaka.events.CancelTripEvent;
import com.example.smartbite.store.kafaka.events.TripCreateEvent;
import com.example.smartbite.store.rider_modul.internalModule.service.RiderService;
import com.example.smartbite.store.rider_modul.internalModule.service.TripRequestService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Slf4j
@Service
public class TripConsumer {

    final private TripRequestService tripRequestService;

    public TripConsumer(TripRequestService tripRequestService) {
        this.tripRequestService = tripRequestService;
    }

    @KafkaListener(
            topics = "trip-event",
            groupId = "rider-service"
    )
    public void listenTripCreateEvent(TripCreateEvent tripCreateEvent) throws IOException {
            log.info("Inside the trip create event listener ");
            log.info("Received trip create event {}", tripCreateEvent);
           tripRequestService.tripAvailableEvent(tripCreateEvent);

           log.info("tripRequestservice called from trip event listener ");
    }


}
