package com.example.smartbite.store.user_modul.mapper;

import com.example.smartbite.store.user_modul.DTO.UserResponseDTO;
import com.example.smartbite.store.user_modul.internalModule.model.Users;
import com.example.smartbite.store.user_modul.publicApi.UserAuthenticationData;

public class UserMapper {

    UserAuthenticationData userEntityToAuthDTO(Users user) {

        return  new  UserAuthenticationData(
                user.getId(),
                user.getName(),
                user.getStatus()
        );
    }
    UserResponseDTO userEntityToResponseDTO(Users user) {
        return  new  UserResponseDTO(
                user.getEmail(),
                user.getName(),
                user.getNumber()
        );
    }
}
