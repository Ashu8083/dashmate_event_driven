package com.example.smartbite.store.kafaka.events;

import java.util.UUID;

public record CancelTripEvent(
        UUID tripId,
        UUID riderId
) {

}
