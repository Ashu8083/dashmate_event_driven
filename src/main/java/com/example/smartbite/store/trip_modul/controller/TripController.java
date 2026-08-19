package com.example.smartbite.store.trip_modul.controller;


import com.example.smartbite.store.trip_modul.DTO.TripRequest;
import com.example.smartbite.store.trip_modul.DTO.TripResponseDTO;
import com.example.smartbite.store.trip_modul.service.interfaces.OrderService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

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

}
