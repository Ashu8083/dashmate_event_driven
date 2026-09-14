package com.example.smartbite.store.trip_modul.internalModule.controller;


import com.example.smartbite.store.helper_service.ChargeCalculation;
import com.example.smartbite.store.trip_modul.DTO.ResponseModelOnCancel;
import com.example.smartbite.store.trip_modul.DTO.TripCancelRequest;
import com.example.smartbite.store.trip_modul.DTO.TripRequest;
import com.example.smartbite.store.trip_modul.DTO.TripResponseDTO;
import com.example.smartbite.store.trip_modul.internalModule.service.interfaces.OrderService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
public class TripController {

    private final OrderService orderService;
    private final ChargeCalculation chargeCalculation;

    public TripController(OrderService orderService,  ChargeCalculation chargeCalculation) {
        this.orderService = orderService;
        this.chargeCalculation = chargeCalculation;
    }

    @PostMapping("/calculate-fare-estimate")
    public Float calculateFare(@RequestBody TripRequest tripRequest){
        return chargeCalculation.chargeCalculate();
    }

    @PostMapping("/create-trip")
    public TripResponseDTO createTrip(@RequestBody TripRequest tripRequest){
        TripResponseDTO tripResponseDTO = orderService.createTripRequest(tripRequest);
        return tripResponseDTO;
    }

    @PostMapping("/cancle-trip")
    public ResponseModelOnCancel cancelTrip(TripCancelRequest tripRequest){
        ResponseModelOnCancel tripResponseDTO = orderService.cancelTripRequest(tripRequest);
        return  tripResponseDTO;
    }


}
