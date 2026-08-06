package com.example.smartbite.store.user_modul.model;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "user_address")
public class UserAddress {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id", unique = true)
    private UUID id ;

    @Column(name = "house_number",nullable = true)
    private String house_number;

    @Column(name = "street",nullable = true)
    private String street;

    @Column(name = "state",nullable = true)
    private String state;

}
