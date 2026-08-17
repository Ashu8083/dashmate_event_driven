package com.example.smartbite.store.trip_modul.provider;

import com.example.smartbite.store.user_modul.DTO.UserResponseDTO;
import com.example.smartbite.store.user_modul.model.Users;
import com.example.smartbite.store.user_modul.repo.UserRepo;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class CustomerProviderImp implements CustomerProvider {

    final UserRepo userRepo;

    public CustomerProviderImp(UserRepo userRepo) {
        this.userRepo = userRepo;
    }
    @Override
    public Users getCustomer(UUID customerId) {
        Users user = new Users();
        user = userRepo.findById(customerId).
                orElseThrow(() -> new RuntimeException("Customer not found"));
        return user;
    }
    @Override
    public Boolean customerIsExist() {
        return null;
    }
}
