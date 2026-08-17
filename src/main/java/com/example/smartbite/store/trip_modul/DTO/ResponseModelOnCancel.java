package com.example.smartbite.store.trip_modul.DTO;

import com.example.smartbite.store.trip_modul.enums.OrderStatus;

import java.time.Instant;
import java.util.UUID;

public class ResponseModelOnCancel {

    public String userName;
    public UUID tripId;
    public String RiderName;
    public UUID riderId;

    public OrderStatus orderStatus = OrderStatus.CANCELED;

    public String package_description;
    public Instant scheduled_time;
    public Instant cancelled_at;
}
