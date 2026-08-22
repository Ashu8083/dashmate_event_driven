package com.example.smartbite.store.user_modul.publicApi;

import com.example.smartbite.store.user_modul.enums.UserStatusEnum;

import java.util.UUID;

public record UserAuthenticationData(
        UUID user_id,
        String user_name,
        UserStatusEnum user_status
        //,UserRoleEnum user_role
) {
}
