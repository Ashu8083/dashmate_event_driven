package com.example.smartbite.store.trip_modul.event;

import com.example.smartbite.store.trip_modul.DTO.PickUpAndDropDTO;
import com.example.smartbite.store.trip_modul.DTO.TripRequest;
import com.example.smartbite.store.trip_modul.model.Trips;

public record TripAvailableEvent(String package_description,
                                 PickUpAndDropDTO pickUpAndDropDTO) { }

