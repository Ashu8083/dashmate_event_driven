package com.example.smartbite.store.rider_modul.DTO;

import com.example.smartbite.store.rider_modul.internalModule.enums.Gender;

import java.util.UUID;

public record RiderModelReplicaDTO(
        UUID rider_id,
        Integer age,
        Gender gander
) {


}
