package com.example.smartbite.store.trip_modul.internalModule.service.interfaces;

import com.example.smartbite.store.trip_modul.DTO.ResponseModelOnCancel;
import com.example.smartbite.store.trip_modul.DTO.TripCancelRequest;
import com.example.smartbite.store.trip_modul.DTO.TripRequest;
import com.example.smartbite.store.trip_modul.DTO.TripResponseDTO;

import java.util.UUID;



public interface OrderService {


    TripResponseDTO createTripRequest(TripRequest tripRequestModel);

    TripResponseDTO assignRider(UUID riderId , UUID tripId ,UUID userId);

    ResponseModelOnCancel cancelTripRequest(TripCancelRequest tripCancelRequest);


}
