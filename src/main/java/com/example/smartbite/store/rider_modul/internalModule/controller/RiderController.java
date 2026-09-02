package com.example.smartbite.store.rider_modul.internalModule.controller;


import com.example.smartbite.store.rider_modul.DTO.RiderAvailableDTO;
import com.example.smartbite.store.rider_modul.DTO.RiderCreateRequestDTO;
import com.example.smartbite.store.rider_modul.DTO.RiderDTO;
import com.example.smartbite.store.rider_modul.internalModule.service.RiderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
public class RiderController {
    final private RiderService riderService;


    public RiderController(RiderService riderService){
        this.riderService = riderService;
    }
    @PostMapping("/mark-rider-available")
    public RiderDTO makeRiderAvailable(RiderAvailableDTO riderAvailableDTO){
            RiderDTO riderDTO = riderService.makeActiveRider(riderAvailableDTO);
            return riderDTO;
    }

    @PostMapping("/create-rider-profile")
    public RiderDTO createRider(RiderCreateRequestDTO riderCreateDTO){
        RiderDTO rider = riderService.createRider(riderCreateDTO);
        return rider;
    }

    @PostMapping("/mark-rider-unavailable")
    public RiderDTO makeRiderUnavailable(RiderAvailableDTO riderAvailableDTO){
        RiderDTO rider = riderService.makeRiderInactive(riderAvailableDTO);
        return rider;
    }



}
