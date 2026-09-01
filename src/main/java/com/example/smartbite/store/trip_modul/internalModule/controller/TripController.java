package com.example.smartbite.store.trip_modul.internalModule.controller;


import com.example.smartbite.store.trip_modul.DTO.ResponseModelOnCancel;
import com.example.smartbite.store.trip_modul.DTO.TripCancelRequest;
import com.example.smartbite.store.trip_modul.DTO.TripRequest;
import com.example.smartbite.store.trip_modul.DTO.TripResponseDTO;
import com.example.smartbite.store.trip_modul.internalModule.service.interfaces.OrderService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
public class TripController {

    private final OrderService orderService;

    public TripController(OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping("/create-trip")
    public TripResponseDTO createTrip(TripRequest tripRequest){
        TripResponseDTO tripResponseDTO = orderService.createTripRequest(tripRequest);
        return tripResponseDTO;
    }

    @PostMapping("/cancle-trip")
    public ResponseModelOnCancel cancelTrip(TripCancelRequest tripRequest){
        ResponseModelOnCancel tripResponseDTO = orderService.cancelTripRequest(tripRequest);
        return  tripResponseDTO;
    }

    @PostMapping("/trip-accespt")
    public TripResponseDTO acceptTrip(UUID riderID, UUID tripID){
        TripResponseDTO tripResponseDTO = orderService.assignRider(riderID,tripID);
        return tripResponseDTO;
    }

}
