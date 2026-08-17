package com.example.smartbite.store.trip_modul.provider;

import com.example.smartbite.store.payment_modul.model.Payment;
import com.example.smartbite.store.payment_modul.repo.PaymentRepo;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.UUID;


@Component
public class PaymentProvideImp implements PaymentProvider {

    final PaymentRepo paymentRepo;
    public PaymentProvideImp(PaymentRepo paymentRepo) {
        this.paymentRepo = paymentRepo;
    }
    @Override
    public Payment getPayment(UUID paymentId) {

        Payment payment = paymentRepo.findById(paymentId)
                .orElseThrow(()->new RuntimeException("Payment Details Not Found "));
        return payment;
    }
}
