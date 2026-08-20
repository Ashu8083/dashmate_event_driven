package com.example.smartbite.store.trip_modul.listener;


import com.example.smartbite.store.rider_modul.event.RiderAvailable;
import com.example.smartbite.store.rider_modul.service.RiderService;
import com.example.smartbite.store.trip_modul.model.TripAssign;
import com.example.smartbite.store.trip_modul.service.interfaces.OrderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class EventListenerForTrip {

    private final OrderService orderService;

    @EventListener
    public void tripAssign(RiderAvailable event){
        log.info("TripAssign event received");
        orderService.assignRider(event.riderDTO(),event.trips_id(),event.tripResponseDTO());

    }

}