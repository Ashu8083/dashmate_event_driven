package com.example.smartbite.store.kafaka.consumer;

import com.example.smartbite.store.kafaka.events.TripAssignEvent;
import com.example.smartbite.store.trip_modul.internalModule.service.interfaces.OrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;


@Slf4j
@Service
public class RiderConsumer {

    private final OrderService orderService;

    public RiderConsumer(OrderService orderService) {
        this.orderService = orderService;
    }



}
