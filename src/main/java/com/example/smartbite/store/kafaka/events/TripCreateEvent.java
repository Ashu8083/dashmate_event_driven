package com.example.smartbite.store.kafaka.events;

import com.example.smartbite.store.payment_modul.internalModule.model.Payment;
import com.example.smartbite.store.trip_modul.DTO.PickUpAndDropDTO;
import com.example.smartbite.store.trip_modul.DTO.TripResponseDTO;

import java.util.UUID;

public record TripCreateEvent(
        String package_description,
        PickUpAndDropDTO pickUpAndDropDTO,
        UUID trip_id, Payment payment,
        TripResponseDTO tripResponseDTO
) {
}
