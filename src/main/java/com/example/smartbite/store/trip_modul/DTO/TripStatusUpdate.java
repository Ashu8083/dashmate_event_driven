package com.example.smartbite.store.trip_modul.DTO;

import com.example.smartbite.store.trip_modul.internalModule.enums.OrderStatus;

import java.util.UUID;

public record TripStatusUpdate(
       UUID tripId,
       UUID riderID,
       OrderStatus tripStatus

) {
}
