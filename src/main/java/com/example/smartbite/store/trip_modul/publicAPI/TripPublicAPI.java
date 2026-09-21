package com.example.smartbite.store.trip_modul.publicAPI;

import com.example.smartbite.store.trip_modul.DTO.TripAssignModelReplica;
import com.example.smartbite.store.trip_modul.DTO.TripModelDTO;
import com.example.smartbite.store.trip_modul.DTO.TripResponseDTO;
import com.example.smartbite.store.trip_modul.DTO.TripStatusUpdate;

import java.util.UUID;

public interface TripPublicAPI {

    TripResponseDTO getTripResponse(UUID tripId);

    TripModelDTO getTripModelDTO(UUID tripId);

    TripAssignModelReplica getTripAssignToRider(UUID rider_id);

    void getTripStatusUpdate(TripStatusUpdate tripStatusUpdate);
}
