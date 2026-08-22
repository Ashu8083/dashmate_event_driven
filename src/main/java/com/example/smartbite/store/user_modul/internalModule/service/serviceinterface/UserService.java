package com.example.smartbite.store.user_modul.internalModule.service.serviceinterface;

import com.example.smartbite.store.user_modul.DTO.*;
import com.example.smartbite.store.user_modul.enums.UserStatusEnum;
import jakarta.transaction.Transactional;

import java.util.UUID;


public interface UserService {


    UserResponseDTO getUserByEmail(String email);

    UserCreateRequestDTO createUser(UserCreateRequestDTO data);

    UserResponseDTO updateUser(UserCreateRequestDTO data);

    UserResponseDTO findUserByName(String name, UserStatusEnum status);

    UserAddressResponseDTO createUserAddress(UUID userId, UserAddressRequestDTO data);

    UserStatusResponse checkUserStatus(String email);

    UserStatusResponse inactivateUser(String email);

    @Transactional
    UserStatusResponse activateUser(String email);
}
