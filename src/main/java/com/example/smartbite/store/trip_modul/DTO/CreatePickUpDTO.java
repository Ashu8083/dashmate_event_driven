package com.example.smartbite.store.trip_modul.DTO;

import com.example.smartbite.store.trip_modul.enums.StopType;

import javax.print.DocFlavor;

public record CreatePickUpDTO(
        StopType stopType,
        String address,
        Double latitude,
        Double longitude,
        String contact_name,
        String contact_phone ) {
}
