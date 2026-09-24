package com.example.smartbite.store.trip_modul.internalModule.controller;


import com.example.smartbite.store.helper_service.ChargeCalculation;
import com.example.smartbite.store.trip_modul.DTO.*;
import com.example.smartbite.store.trip_modul.internalModule.service.interfaces.HistoryTripService;
import com.example.smartbite.store.trip_modul.internalModule.service.interfaces.OrderService;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.awt.print.Pageable;
import java.util.UUID;

@RestController
public class TripController {

    private final OrderService orderService;
    private final ChargeCalculation chargeCalculation;
    private final HistoryTripService historyTripService;

    public TripController(OrderService orderService,
                          ChargeCalculation chargeCalculation,
                            HistoryTripService historyTripService) {
        this.orderService = orderService;
        this.chargeCalculation = chargeCalculation;
        this.historyTripService = historyTripService;
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

    @GetMapping("/get-customer-trips")
    public Page<TripHistoryResponesDTO> getCustomerTrips(@RequestParam UUID customerID, Pageable pageable){
        return  historyTripService.getCustomerTrip(customerID, pageable);
    }


}
