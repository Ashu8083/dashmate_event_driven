package com.example.smartbite.store.kafaka.events;
import java.util.UUID;

public record CreatePaymentEvent(
        Float charge,
        UUID user_Id,
        UUID trip_Id
) {
}
