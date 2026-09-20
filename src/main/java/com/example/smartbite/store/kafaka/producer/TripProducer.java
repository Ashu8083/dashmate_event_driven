package com.example.smartbite.store.kafaka.producer;


import com.example.smartbite.store.kafaka.events.TripCreateEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;


@Slf4j
@Service
public class TripProducer {

    final private KafkaTemplate<String, TripCreateEvent> kafkaTemplate;

    public TripProducer(KafkaTemplate<String, TripCreateEvent> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }
    public void sendTripCreateEvent(TripCreateEvent tripCreateEvent) {


        log.info("Inside TripProducer sendTripCreateEvent {}", tripCreateEvent);
        log.info("Sending trip event {}", tripCreateEvent);
        kafkaTemplate.send("trip-event",
                                    tripCreateEvent.trip_id().toString(),
                                    tripCreateEvent);

        log.info("trip producer called from sendTripCreateEvent Service");
    }


}
