package com.example.smartbite.store.kafaka.producer;


import com.example.smartbite.store.kafaka.events.TripAssignEvent;
import com.example.smartbite.store.kafaka.events.TripEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.security.PublicKey;


@Slf4j
@Service
public class TripProducer {

    final private KafkaTemplate<String, TripEvent> kafkaTemplate;

    public TripProducer(KafkaTemplate<String, TripEvent> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }
    public void sendTripCreateEvent(TripEvent event) {
//
        log.info("Inside TripProducer sendTripCreateEvent {}", event);
        log.info("Sending trip event {}", event);
        kafkaTemplate.send("trip-event",
                                    event);

        log.info("trip producer called from sendTripCreateEvent Service");
    }

    public void sendtTripAssignEvent(TripEvent event) {
        log.info("Inside TripProducer sendTripAssignEvent {}", event);
        log.info("Sending trip assign event {}", event);
        kafkaTemplate.send("trip-event",
                                event);
    }


}
