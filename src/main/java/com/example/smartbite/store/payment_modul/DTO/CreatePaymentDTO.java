package com.example.smartbite.store.payment_modul.DTO;

public record CreatePaymentDTO(
        String payment_method,
        Double total_charge,
        Boolean is_payment_successful
) {
}
