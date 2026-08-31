package com.example.smartbite.store.rider_modul.internalModule.controller;


import com.example.smartbite.store.rider_modul.internalModule.DTO.RiderAvailableDTO;
import com.example.smartbite.store.rider_modul.internalModule.DTO.RiderDTO;
import com.example.smartbite.store.rider_modul.internalModule.DTO.RiderResponseDTO;
import com.example.smartbite.store.rider_modul.internalModule.service.RiderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
public class RiderController {
    final private RiderService riderService;


    public RiderController(RiderService riderService){
        this.riderService = riderService;
    }
    @PostMapping("/rider-available")
    public RiderDTO makeRiderAvailable(RiderAvailableDTO riderAvailableDTO){
            RiderDTO riderDTO = riderService.makeActiveRider(riderAvailableDTO);
            return riderDTO;
    }



}
