package com.example.smartbite.store.payment_modul.internalModule.repo;

import com.example.smartbite.store.payment_modul.internalModule.model.Payment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface PaymentRepo extends JpaRepository<Payment, UUID> {


}
