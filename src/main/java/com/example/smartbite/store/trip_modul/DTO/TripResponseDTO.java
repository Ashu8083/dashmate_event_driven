package com.example.smartbite.store.trip_modul.DTO;

import com.example.smartbite.store.rider_modul.enums.Gender;
import com.example.smartbite.store.trip_modul.enums.OrderStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.util.Map;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TripResponseDTO {

    public UUID customerId;
    public UUID tripId;
    public String RiderName = null;
    public UUID riderId;
    public Gender rider_gander;
    public OrderStatus orderStatus;
    public String package_description;
    public String pickUpAddress;
    public Map<String,Double> pickUpLocations;
    public String dropOffAddress;
    public Map<String,Double> dropOffLocations;
    public Instant scheduled_time;

}
