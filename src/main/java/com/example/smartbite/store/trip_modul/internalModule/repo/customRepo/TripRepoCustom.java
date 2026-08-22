package com.example.smartbite.store.trip_modul.internalModule.repo.customRepo;

import com.example.smartbite.store.trip_modul.internalModule.enums.OrderStatus;
import com.example.smartbite.store.trip_modul.internalModule.model.Trips;

import java.util.List;
import java.util.UUID;

public interface TripRepoCustom {

    List<Trips> getDeliveredTripByCustomerId(UUID customerId, OrderStatus status);
}
