package com.example.smartbite.store.kafaka.consumer;


import com.example.smartbite.store.kafaka.events.CancelTripEvent;
import com.example.smartbite.store.kafaka.events.TripCreateEvent;
import com.example.smartbite.store.rider_modul.internalModule.service.RiderService;
import com.example.smartbite.store.rider_modul.internalModule.service.TripRequestService;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class TripConsumer {

    final private RiderService riderService;
    final private TripRequestService tripRequestService;

    public TripConsumer(RiderService riderService , TripRequestService tripRequestService) {
        this.riderService = riderService;
        this.tripRequestService = tripRequestService;
    }

    @KafkaListener(
            topics = "trip-event",
            groupId = "rider-service"
    )
    public void listenTripCreateEvent(TripCreateEvent tripCreateEvent) throws IOException {
           tripRequestService.tripAvailableEvent(tripCreateEvent);
    }

    @KafkaListener(
            topics = "trip-event",
            groupId = "rider-service"
    )
    public void cancelTripCreateEvent(CancelTripEvent event) {
    }


}
