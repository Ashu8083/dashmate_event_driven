package com.example.smartbite.store.kafaka.events;

import com.example.smartbite.store.rider_modul.internalModule.enums.Gender;

import java.util.UUID;

public record TripAssignEvent(
        UUID tripId,
        UUID rider_id,
        UUID user_id,
        Integer age,
        Gender gander
) {
}
