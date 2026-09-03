package com.example.smartbite.store.rider_modul.internalModule.model;


import com.example.smartbite.store.rider_modul.internalModule.enums.Gender;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.util.UUID;

@Entity
@Table(name = "riders")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Riders {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(
            name = "user_id",
            nullable = false,
            unique = true
    )
    private UUID userId;

// test for git commit

    @Column(name = "age")
    private Integer age;

    @Column(name = "is_available", nullable = false)
    private Boolean isAvailable = false;

    @Column(name = "latitude",nullable = true)
    private Double latitude;

    @Column(name = "longitude",nullable = true)
    private Double longitude;

    @Enumerated(EnumType.STRING)
    @JdbcTypeCode(SqlTypes.NAMED_ENUM)
    @Column(name = "gender")
    private Gender gender;
}