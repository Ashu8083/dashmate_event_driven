package com.example.smartbite.store.payment_modul.model;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name ="payment")
public class Payment {

    @Id
    private UUID id;

    @Column(name = "payment_method")
    private String payment_method;

    @Column(name = "total_charge")
    private Double total_charge;

    @Column(name = "is_payment_successful")
    private Boolean is_payment_successful;
}
