package com.example.smartbite.store.rider_modul.DTO;

import com.example.smartbite.store.rider_modul.enums.Gender;

import java.util.UUID;

public record RiderResponseDTO(
        UUID id,
        Boolean isAvailable,
        Gender gender
) {
}
