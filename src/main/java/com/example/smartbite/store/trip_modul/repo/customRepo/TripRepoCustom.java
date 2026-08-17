package com.example.smartbite.store.trip_modul.repo.customRepo;

import com.example.smartbite.store.trip_modul.enums.OrderStatus;
import com.example.smartbite.store.trip_modul.model.Trips;

import java.util.List;
import java.util.UUID;

public interface TripRepoCustom {

    List<Trips> getDeliveredTripByCustomerId(UUID customerId, OrderStatus status);
}
