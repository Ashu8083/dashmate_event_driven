package com.example.smartbite.store.payment_modul.repo;

import com.example.smartbite.store.payment_modul.model.Payment;
import org.jspecify.annotations.NonNull;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface PaymentRepo extends JpaRepository<Payment, UUID> {

    Optional<Payment> findById(@NonNull UUID id);
}
