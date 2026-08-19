package com.example.smartbite.store.trip_modul.DTO;

import com.example.smartbite.store.rider_modul.model.Riders;
import com.example.smartbite.store.trip_modul.model.Trips;

public record AssignRiderDTO(
        Riders rider,
        Trips trips
) {
}
