package com.example.smartbite.store.kafaka.events;

import com.example.smartbite.store.kafaka.enums.TripTypeEvent;
import com.fasterxml.jackson.databind.JsonNode;

public record TripEvent(
        TripTypeEvent type,
        JsonNode payload
) {
}
