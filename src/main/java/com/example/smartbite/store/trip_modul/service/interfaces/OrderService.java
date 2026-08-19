package com.example.smartbite.store.trip_modul.service.interfaces;

import com.example.smartbite.store.rider_modul.model.Riders;
import com.example.smartbite.store.trip_modul.DTO.*;
import org.springframework.stereotype.Service;

import java.util.UUID;


@Service
public interface OrderService {


    TripResponseDTO createTripRequest(TripRequest tripRequestModel);

    TripResponseDTO assignRider(Riders rider,UUID tripId,TripResponseDTO tripResponseDTO);

    ResponseModelOnCancel cancelTripRequest(TripCancelRequest tripCancelRequest);



}
