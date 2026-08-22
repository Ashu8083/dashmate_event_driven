package com.example.smartbite.store.rider_modul.internalModule.event;

import com.example.smartbite.store.rider_modul.internalModule.DTO.RiderDTO;
import com.example.smartbite.store.trip_modul.DTO.TripResponseDTO;
import lombok.extern.slf4j.Slf4j;

import java.util.UUID;


@Slf4j
public record RiderAvailable (
   RiderDTO riderDTO,
    UUID trips_id,
    TripResponseDTO tripResponseDTO
    ){
}
