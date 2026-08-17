package com.example.smartbite.store.trip_modul.provider;

import com.example.smartbite.store.payment_modul.model.Payment;

import java.util.UUID;

public interface PaymentProvider {

    Payment getPayment(UUID paymentId);
}
