package com.example.smartbite.store.kafaka.producer;

import com.example.smartbite.store.kafaka.events.TripAssignEvent;
import org.springframework.kafka.core.KafkaTemplate;

public class RiderProducer {

    KafkaTemplate<String, TripAssignEvent> kafkaTemplate;
    public RiderProducer(KafkaTemplate<String, TripAssignEvent> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void tripAssigned (TripAssignEvent tripAssign) {
        kafkaTemplate.send("trip-assigned", tripAssign);
    }


}
