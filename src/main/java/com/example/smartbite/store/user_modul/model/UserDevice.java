package com.example.smartbite.store.user_modul.model;

import com.example.smartbite.store.user_modul.enums.DeviceTypeEnum;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "user_device")
public class UserDevice {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;


    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private Users users;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "refresh_token_id",nullable = false)
    private RefreshToken refreshToken;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private DeviceTypeEnum device_type ;

    private Instant last_login ;

    private Instant expire_date;

    private Instant created_date;

}