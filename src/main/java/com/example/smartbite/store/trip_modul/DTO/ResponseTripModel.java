package com.example.smartbite.store.trip_modul.DTO;


import com.example.smartbite.store.trip_modul.enums.Gander;
import com.example.smartbite.store.trip_modul.enums.OrderStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ResponseTripModel {

    public String userName;
    public UUID tripId;
    public String RiderName;
    public UUID riderId;
    public Gander gander;

    public OrderStatus orderStatus;

    public String package_description;
    public Instant scheduled_time;

}
