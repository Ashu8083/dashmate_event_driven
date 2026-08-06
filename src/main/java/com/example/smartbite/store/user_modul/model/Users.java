package com.example.smartbite.store.user_modul.model;

import jakarta.persistence.*;

import lombok.Data;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.util.UUID;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "users")
public class Users {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "user_name",nullable = false)
    private String name;

    @Column(name = "email", nullable = false,unique = true)
    private String email;

    @Column(name = "phone_number", nullable = true ,unique = true)
    private String number;

}
