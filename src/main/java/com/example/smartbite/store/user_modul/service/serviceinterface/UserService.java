package com.example.smartbite.store.user_modul.service.serviceinterface;

import com.example.smartbite.store.user_modul.DTO.UserAddressRequestDTO;
import com.example.smartbite.store.user_modul.DTO.UserAddressResponseDTO;
import com.example.smartbite.store.user_modul.DTO.UserCreateRequestDTO;
import com.example.smartbite.store.user_modul.DTO.UserResponseDTO;
import com.example.smartbite.store.user_modul.enums.UserStatusEnum;
import com.example.smartbite.store.user_modul.model.RefreshToken;
import com.example.smartbite.store.user_modul.model.UserAddress;
import com.example.smartbite.store.user_modul.model.UserDevice;
import com.example.smartbite.store.user_modul.model.Users;
import org.apache.catalina.User;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;



public interface UserService {


    UserResponseDTO getUserByEmail(String email);

    UserCreateRequestDTO createUser(UserCreateRequestDTO data);

    UserResponseDTO updateUser(UserCreateRequestDTO data);

    UserResponseDTO findUserByName(String name, UserStatusEnum status);

    UserAddressResponseDTO createUserAddress(UserAddressRequestDTO data);


}
