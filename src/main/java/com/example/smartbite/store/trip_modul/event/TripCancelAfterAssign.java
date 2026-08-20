package com.example.smartbite.store.trip_modul.event;

import com.example.smartbite.store.rider_modul.model.Riders;

import java.util.UUID;

public record TripCancelAfterAssign(
        UUID rider_id
) {
}
