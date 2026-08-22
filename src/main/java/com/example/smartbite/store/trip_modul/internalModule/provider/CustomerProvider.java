package com.example.smartbite.store.trip_modul.internalModule.provider;

import com.example.smartbite.store.user_modul.internalModule.model.Users;

import java.util.UUID;


public interface CustomerProvider {
    public Users getCustomer(UUID customerId);
    public Boolean customerIsExist();
}
