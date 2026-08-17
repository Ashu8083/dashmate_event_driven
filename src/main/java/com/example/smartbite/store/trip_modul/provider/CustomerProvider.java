package com.example.smartbite.store.trip_modul.provider;

import com.example.smartbite.store.user_modul.DTO.UserResponseDTO;
import com.example.smartbite.store.user_modul.model.Users;

import java.util.UUID;


public interface CustomerProvider {
    public Users getCustomer(UUID customerId);
    public Boolean customerIsExist();
}
