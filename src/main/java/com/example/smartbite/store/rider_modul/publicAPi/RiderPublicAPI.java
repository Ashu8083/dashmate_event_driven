package com.example.smartbite.store.rider_modul.publicAPi;

import com.example.smartbite.store.rider_modul.DTO.RiderModelReplicaDTO;

import java.util.UUID;

public interface RiderPublicAPI {


    RiderModelReplicaDTO getRiderReplica(UUID riderId);

}
