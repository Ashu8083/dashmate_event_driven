package com.example.smartbite.store.rider_modul.DTO;

import java.util.UUID;

public record RiderAvailableDTO(
        UUID rider_id ,
        Float longitude,
        Float latitude
) {
}
