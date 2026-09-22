package com.example.smartbite.store.rider_modul.DTO;

import java.util.UUID;

public record RiderAvailableDTO(
        UUID rider_id ,
        Double longitude,
        Double latitude
//        Float longitude,
//        Float latitude
) {
}
