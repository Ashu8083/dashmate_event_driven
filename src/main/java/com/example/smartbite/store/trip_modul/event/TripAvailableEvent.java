package com.example.smartbite.store.trip_modul.event;

import com.example.smartbite.store.payment_modul.model.Payment;
import com.example.smartbite.store.trip_modul.DTO.PickUpAndDropDTO;
import com.example.smartbite.store.trip_modul.DTO.TripRequest;
import com.example.smartbite.store.trip_modul.DTO.TripResponseDTO;
import com.example.smartbite.store.trip_modul.model.Trips;

import java.util.UUID;

public record TripAvailableEvent(String package_description,
                                 PickUpAndDropDTO pickUpAndDropDTO,
                                 UUID trip_id, Payment payment,
                                 TripResponseDTO tripResponseDTO) { }

