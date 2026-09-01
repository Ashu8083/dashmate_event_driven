package com.example.smartbite.store.trip_modul.internalModule.model;


import com.example.smartbite.store.trip_modul.internalModule.enums.StopType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.util.UUID;



@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "trip_stops")
public class TripStops {

    @Id
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "trip_id", nullable = false)
    private Trips trip;

    @Enumerated(EnumType.STRING)
    @JdbcTypeCode(SqlTypes.NAMED_ENUM)
    private StopType stopType;

    @Column(name = "address")
    private String  address;

    @Column(name ="latitude")
    private Double latitude;

    @Column(name = "longitude")
    private Double longitude;

    @Column(name = "contact_name")
    private String contact_name;

    @Column(name = "contact_phone")
    private String contact_phone;

}
