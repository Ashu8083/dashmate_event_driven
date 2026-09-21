package com.example.smartbite.store.rider_modul.publicAPi;

import com.example.smartbite.store.rider_modul.DTO.RiderModelReplicaDTO;
import com.example.smartbite.store.trip_modul.DTO.TripStatusUpdate;

import java.util.UUID;

public interface RiderPublicAPI {


    RiderModelReplicaDTO getRiderReplica(UUID riderId);
    void updateRiderStatus(UUID riderId);
}
