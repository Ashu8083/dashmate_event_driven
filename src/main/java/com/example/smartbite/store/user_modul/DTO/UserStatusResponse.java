package com.example.smartbite.store.user_modul.DTO;

import com.example.smartbite.store.user_modul.enums.UserStatusEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserStatusResponse {

    public UUID id;

    public String name;

    public String email;

    public UserStatusEnum status;
}
