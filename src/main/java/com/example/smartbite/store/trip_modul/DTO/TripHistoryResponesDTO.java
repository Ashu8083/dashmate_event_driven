package com.example.smartbite.store.trip_modul.DTO;

import com.example.smartbite.store.trip_modul.internalModule.enums.OrderStatus;

import java.time.Instant;
import java.util.Date;
import java.util.UUID;

public record TripHistoryResponesDTO(
        UUID tripId,
        UUID riderId,
        OrderStatus status,
        PickUpAndDropDTO pickUpAndDropDTO,
        Instant created_at
) {
}
