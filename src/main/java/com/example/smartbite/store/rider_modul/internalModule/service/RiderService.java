package com.example.smartbite.store.rider_modul.internalModule.service;


import com.example.smartbite.store.common.execption.ResourceNotFoundException;
import com.example.smartbite.store.payment_modul.internalModule.model.Payment;
import com.example.smartbite.store.rider_modul.DTO.RiderAvailableDTO;
import com.example.smartbite.store.rider_modul.DTO.RiderCreateRequestDTO;
import com.example.smartbite.store.rider_modul.DTO.RiderDTO;
import com.example.smartbite.store.rider_modul.internalModule.event.RiderAvailable;
import com.example.smartbite.store.rider_modul.mapper.RiderMapper;
import com.example.smartbite.store.rider_modul.internalModule.model.Riders;
import com.example.smartbite.store.rider_modul.internalModule.repo.RiderRepo;
import com.example.smartbite.store.trip_modul.DTO.PickUpAndDropDTO;
import com.example.smartbite.store.trip_modul.DTO.TripResponseDTO;
import com.example.smartbite.store.user_modul.publicApi.UserModuleApiImpl;
import com.example.smartbite.store.user_modul.publicApi.UserModuleReplica;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Slf4j
@Service
public class RiderService {

    private final RiderRepo riderRepo;
    private final ApplicationEventPublisher eventPublisher;
    private final RiderMapper riderMapper;
    private final UserModuleApiImpl userModuleApi;

    public RiderService(RiderRepo riderRepo,ApplicationEventPublisher eventPublisher,
                        RiderMapper riderMappaer ,UserModuleApiImpl userModuleApi  )
    {
        this.riderRepo = riderRepo;
        this.eventPublisher = eventPublisher;
        this.riderMapper = riderMappaer;
        this.userModuleApi = userModuleApi;

    }


    public Riders getAvailableRider(PickUpAndDropDTO pickUpAddress,
                                    Payment payment, UUID trip_id,
                                    TripResponseDTO tripResponseDTO) {
        Riders rider = new Riders();
        RiderDTO riderDTO = new RiderDTO();
        rider = riderRepo.findNearestAvailableRider(pickUpAddress.createDropDTO().latitude()
                        , pickUpAddress.createDropDTO().longitude())
                .orElseThrow(() -> new RuntimeException("Rider not found"));
        riderDTO = riderMapper.entityToDTO(rider);
        log.info("RiderService getAvailableRider and publishing rider event");
        eventPublisher.publishEvent(new RiderAvailable(riderDTO, trip_id, tripResponseDTO));
        log.info("RiderService getAvailableRider and publishing rider event");
        return rider;
    }

    public Riders markInactiveRider(Riders rider) {
        log.info("RiderService markInactiveRider and publishing rider event");
        rider.setIsAvailable(false);
        riderRepo.save(rider);
        log.info("RiderService markInactiveRider and publishing rider event");
        return rider;
    }

    @Transactional
    public RiderDTO makeActiveRider(RiderAvailableDTO riderAvailable) {
        Riders rider = riderRepo.findById(riderAvailable.rider_id()).orElseThrow(() -> new ResourceNotFoundException("Rider not found"));
        log.info("RiderService makeActiveRider and publishing rider event");
        rider.setIsAvailable(true);
        log.info("RiderService makeActiveRider and publishing rider event");

        RiderDTO riderResponse = riderMapper.entityToDTO(rider);
        return riderResponse;
    }

    @Transactional
    public RiderDTO createRider(RiderCreateRequestDTO rider) {

        UserModuleReplica userDTO = userModuleApi.createUserModel(rider.name(), rider.Number(), rider.email());
        if (userDTO == null){
            throw new ResourceNotFoundException("User cannot be created");
        }
        Riders riderModel = new Riders();
        riderModel.setAge(rider.age());
        riderModel.setUserId(userDTO.user_id());
        riderModel.setGender(rider.gender());

        riderRepo.save(riderModel);

        RiderDTO riderResponse = riderMapper.entityToDTO(riderModel);
        return riderResponse;
    }

}
