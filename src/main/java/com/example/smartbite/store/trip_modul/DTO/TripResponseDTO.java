package com.example.smartbite.store.trip_modul.DTO;

import com.example.smartbite.store.rider_modul.internalModule.enums.Gender;
import com.example.smartbite.store.trip_modul.internalModule.enums.OrderStatus;
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

    public UUID tripId;
    public Float Charges = null;
    public String package_description;
    public String pickUpAddress;
    public OrderStatus orderStatus;
    public Map<String,Double> pickUpLocations;
    public String dropOffAddress;
    public Map<String,Double> dropOffLocations;
    public Instant scheduled_time;

}
