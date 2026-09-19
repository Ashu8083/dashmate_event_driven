package com.example.smartbite.store.kafaka.consumer;


import com.example.smartbite.store.kafaka.events.CancelTripEvent;
import com.example.smartbite.store.kafaka.events.TripCreateEvent;
import com.example.smartbite.store.rider_modul.internalModule.service.RiderService;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class TripConsumer {

    final private RiderService riderService;

    public TripConsumer(RiderService riderService) {
        this.riderService = riderService;
    }

    @KafkaListener(
            topics = "trip-event",
            groupId = "rider-service"
    )
    public void listenTripCreateEvent(TripCreateEvent tripCreateEvent) {
            riderService.getAvailableRider(tripCreateEvent.pickUpAndDropDTO());
    }

    @KafkaListener(
            topics = "trip-event",
            groupId = "rider-service"
    )
    public void cancelTripCreateEvent(CancelTripEvent event) {
    }


}
