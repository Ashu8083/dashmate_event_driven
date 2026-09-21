package com.example.smartbite.store.trip_modul.internalModule.service.interfaces;

import com.example.smartbite.store.kafaka.events.TripAssignEvent;
import com.example.smartbite.store.trip_modul.DTO.*;

import java.util.UUID;



public interface OrderService {
    TripResponseDTO createTripRequest(TripRequest tripRequestModel);
    TripResponseDTO assignRider(TripAssignEvent tripAssignEvent);
    TripResponseDTO updateOrderStatus(TripStatusUpdate tripStatusUpdate);
    ResponseModelOnCancel cancelTripRequest(TripCancelRequest tripCancelRequest);


}
