package com.example.smartbite.store.trip_modul.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TripRequest {

    public UUID customer_id;
    public String package_description;
    public Instant scheduled_time;
}