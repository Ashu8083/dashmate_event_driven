package com.example.smartbite.store.trip_modul.model;


import com.example.smartbite.store.payment_modul.model.Payment;
import com.example.smartbite.store.rider_modul.model.Riders;
import com.example.smartbite.store.trip_modul.enums.OrderStatus;
import com.example.smartbite.store.user_modul.model.Users;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.time.Instant;
import java.util.List;
import java.util.UUID;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "trips")
public class Trips {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @OneToOne
    @JoinColumn(name = "customer_id")
    private Users customer;

    @OneToOne
    @JoinColumn(name = "trip_assign_id")
    private  TripAssign tripAssign;


    @OneToOne
    @JoinColumn(name = "payment_id")
    private Payment payment;

    @Enumerated(EnumType.STRING)
    @JdbcTypeCode(SqlTypes.NAMED_ENUM)
    private OrderStatus status;

    @Column(name= "package_description")
    private String package_description;

    @Column(name= "scheduled_at")
    private Instant scheduled_at;

    @Column(name= "created_at")
    private Instant created_at;


    @Column(name= "updated_at")
    private Instant updated_at;
    @Column(name= "cancelled_at")
    private Instant cancelled_at;

    @Column(name= "completed_at")
    private Instant completed_at;


}
