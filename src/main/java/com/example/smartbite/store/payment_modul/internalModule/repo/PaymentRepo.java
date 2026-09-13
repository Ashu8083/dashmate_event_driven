package com.example.smartbite.store.payment_modul.internalModule.repo;

import com.example.smartbite.store.payment_modul.internalModule.model.Payment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface PaymentRepo extends JpaRepository<Payment, UUID> {

    Optional<Payment> findByStatus(String payment_method);

    Optional<Payment> findByIDAndStatus(UUID id, String payment_method);


}
