package com.example.smartbite.store.user_modul.DTO;

import com.example.smartbite.store.user_modul.enums.UserStatusEnum;

import java.util.UUID;

public class UserStatusResponse {

    public UUID id;

    public String name;

    public String email;

    public UserStatusEnum status;

}
