package com.example.smartbite.store.trip_modul.DTO;

import com.example.smartbite.store.trip_modul.enums.StopType;

public record CreatePickUpDTO(
        StopType stopType,
        String address,
        Double latitude,
        Double longitude,
        Double contact_name,
        Double contact_phone ) {
}
