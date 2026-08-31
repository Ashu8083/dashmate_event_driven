package com.example.smartbite.store.user_modul.internalModule.event;

import com.example.smartbite.store.rider_modul.internalModule.enums.Gender;

import java.util.UUID;

public record RiderCreateEvent(
        UUID user_id,
        Integer age,
        Gender gender
) {
}
