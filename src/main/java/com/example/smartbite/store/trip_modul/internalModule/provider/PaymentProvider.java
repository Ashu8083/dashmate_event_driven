package com.example.smartbite.store.trip_modul.internalModule.provider;

import com.example.smartbite.store.payment_modul.internalModule.model.Payment;

import java.util.UUID;

public interface PaymentProvider {

    Payment getPayment(UUID paymentId);
    Boolean checkPayment(UUID paymentId);
}
