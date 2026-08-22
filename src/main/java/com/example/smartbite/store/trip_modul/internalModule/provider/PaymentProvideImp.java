package com.example.smartbite.store.trip_modul.internalModule.provider;

import com.example.smartbite.store.payment_modul.internalModule.model.Payment;
import com.example.smartbite.store.payment_modul.internalModule.repo.PaymentRepo;
import org.springframework.stereotype.Component;

import java.util.UUID;


@Component
public class PaymentProvideImp implements PaymentProvider {

    final PaymentRepo paymentRepo;
    public PaymentProvideImp(PaymentRepo paymentRepo) {
        this.paymentRepo = paymentRepo;
    }
    @Override
    public Payment getPayment(UUID paymentId) {

        return paymentRepo.findById(paymentId)
                .orElseThrow(()->new RuntimeException("Payment Details Not Found "));
    }

    @Override
    public Boolean checkPayment(UUID paymentId) {
        return paymentRepo.existsById(paymentId);
    }
}
