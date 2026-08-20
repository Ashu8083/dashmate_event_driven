package com.example.smartbite.store.rider_modul.service;


import com.example.smartbite.store.payment_modul.model.Payment;
import com.example.smartbite.store.rider_modul.DTO.RiderDTO;
import com.example.smartbite.store.rider_modul.event.RiderAvailable;
import com.example.smartbite.store.rider_modul.mapper.RiderMapper;
import com.example.smartbite.store.rider_modul.model.Riders;
import com.example.smartbite.store.rider_modul.repo.RiderRepo;
import com.example.smartbite.store.trip_modul.DTO.PickUpAndDropDTO;
import com.example.smartbite.store.trip_modul.DTO.TripResponseDTO;
import com.example.smartbite.store.trip_modul.model.Trips;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Slf4j
@Service
public class RiderService {

    private final RiderRepo riderRepo;
    private final ApplicationEventPublisher eventPublisher;
    private final RiderMapper riderMapper;

    public RiderService(RiderRepo riderRepo, ApplicationEventPublisher applicationEventPublisher,RiderMapper riderMapper) {
        this.riderRepo = riderRepo;
        this.eventPublisher = applicationEventPublisher;
        this.riderMapper = riderMapper;
    }

    public Riders getAvailableRider (PickUpAndDropDTO pickUpAddress, Payment payment, UUID trip_id, TripResponseDTO tripResponseDTO){
        Riders rider = new Riders();
        RiderDTO riderDTO = new RiderDTO();
        rider = riderRepo.findNearestAvailableRider(pickUpAddress.createDropDTO().latitude()
                                                    ,pickUpAddress.createDropDTO().longitude())
                                                    .orElseThrow(()-> new RuntimeException("Rider not found"));
        riderDTO = riderMapper.entityToDTO(rider);
        log.info("RiderService getAvailableRider and publishing rider event");
        eventPublisher.publishEvent(new RiderAvailable(riderDTO,trip_id,tripResponseDTO));
        log.info("RiderService getAvailableRider and publishing rider event");
        return rider;
    }
    public Riders markInactiveRider (Riders rider){
        log.info("RiderService markInactiveRider and publishing rider event");
        rider.setIsAvailable(false);
        riderRepo.save(rider);
        log.info("RiderService markInactiveRider and publishing rider event");
        return rider;
    }
    public Riders makeActiveRider (Riders rider){
        log.info("RiderService makeActiveRider and publishing rider event");
        rider.setIsAvailable(true);
        riderRepo.save(rider);
        log.info("RiderService makeActiveRider and publishing rider event");
        return rider;
    }
    public Riders createRider (){

        return riderRepo.save(new Riders());
    }


}
