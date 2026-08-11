package com.example.smartbite.store.user_modul.repo;

import com.example.smartbite.store.user_modul.model.UserAddress;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface UserAddressRepo extends JpaRepository<UserAddress, UUID> {
}
