package com.example.smartbite.store.rider_modul.internalModule.listener;


import com.example.smartbite.store.rider_modul.internalModule.service.RiderService;
import com.example.smartbite.store.trip_modul.internalModule.event.TripAvailableEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class RiderListener {
    private  final RiderService riderService;

    @EventListener
    public void onTripRequestCreate(TripAvailableEvent event) {
        log.info("Received trip available event: {}", event);
        riderService.getAvailableRider(event.pickUpAndDropDTO()
                                        ,event.payment(),event.trip_id(),event.tripResponseDTO());

    }
}
