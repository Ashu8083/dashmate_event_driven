package com.example.smartbite.store.kafaka.producer;


import com.example.smartbite.store.kafaka.events.TripCreateEvent;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class TripProducer {

    final private KafkaTemplate<String, TripCreateEvent> kafkaTemplate;

    public TripProducer(KafkaTemplate<String, TripCreateEvent> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }
    public void sendTripCreateEvent(TripCreateEvent tripCreateEvent) {
        kafkaTemplate.send("trip-event",
                                    tripCreateEvent.trip_id().toString(),
                                    tripCreateEvent);

    }
    public void cancelTripCreateEvent() {}

}
