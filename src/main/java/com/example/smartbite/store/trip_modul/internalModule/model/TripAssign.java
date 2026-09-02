package com.example.smartbite.store.trip_modul.internalModule.model;


import com.example.smartbite.store.trip_modul.internalModule.enums.OrderStatus;
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
    private UUID riderId;

    @ManyToOne
    @JoinColumn(name = "trip_id", nullable = false, unique = true)
    private Trips trip;

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
