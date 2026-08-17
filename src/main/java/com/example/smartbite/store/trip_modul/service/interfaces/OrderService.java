package com.example.smartbite.store.trip_modul.service.interfaces;

import com.example.smartbite.store.trip_modul.DTO.ResponseModelOnCancel;
import com.example.smartbite.store.trip_modul.DTO.ResponseTripModel;
import com.example.smartbite.store.trip_modul.DTO.TripCancelRequest;
import com.example.smartbite.store.trip_modul.DTO.TripRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;


@Service
public interface OrderService {


    ResponseTripModel createTripRequest(TripRequest tripRequestModel);

    ResponseTripModel updateTripRequest(TripRequest tripRequestModel);

    ResponseModelOnCancel cancelTripRequest(TripCancelRequest tripCancelRequest);

    List<ResponseTripModel> getAllTripRequestsByUserId(UUID userId );



}
