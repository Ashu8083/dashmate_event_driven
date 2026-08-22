package com.example.smartbite.store.user_modul.publicApi;

import java.util.UUID;

public record UserModuleReplica(
        UUID user_id,
        String user_name
) {
}
