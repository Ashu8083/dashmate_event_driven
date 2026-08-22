package com.example.smartbite.store.trip_modul.DTO;

import com.example.smartbite.store.rider_modul.internalModule.model.Riders;
import com.example.smartbite.store.trip_modul.internalModule.model.Trips;

public record AssignRiderDTO(
        Riders rider,
        Trips trips
) {
}
