package com.example.smartbite.store.payment_modul.internalModule.repo;

import com.example.smartbite.store.payment_modul.internalModule.model.Payment;
import com.example.smartbite.store.trip_modul.internalModule.enums.OrderStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface PaymentRepo extends JpaRepository<Payment, UUID> {

    Optional<Payment> findByStatus(OrderStatus payment_method);
    Optional<Payment> findByIdAndStatus(UUID id, OrderStatus payment_method);


}
