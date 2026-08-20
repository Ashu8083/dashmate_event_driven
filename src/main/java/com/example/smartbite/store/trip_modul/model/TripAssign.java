package com.example.smartbite.store.trip_modul.model;


import com.example.smartbite.store.rider_modul.model.Riders;
import com.example.smartbite.store.trip_modul.enums.OrderStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name= "trip_assign")
public class TripAssign {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name= "rider_id")
    private UUID rider_id;


    @Column(name = "trip_id", nullable = false, unique = true)
    private UUID trip_id;

    @Column(name = "status")
    private OrderStatus status;

    @Column(name= "created_at")
    private LocalDateTime created_at;

    @Column(name = "updated_at")
    private LocalDateTime updated_at;

    @Column(name = "cancelled_at")
    private LocalDateTime cancelled_at;

    @Column(name = "completed_at")
    private LocalDateTime completed_at;
}
