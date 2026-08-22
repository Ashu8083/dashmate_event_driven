package com.example.smartbite.store.user_modul.internalModule.repo;

import com.example.smartbite.store.user_modul.internalModule.model.RefreshToken;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface UserRefreshTokenRepo extends JpaRepository<RefreshToken, UUID> {

}
