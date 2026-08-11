package com.example.smartbite.store.user_modul.repo;

import com.example.smartbite.store.user_modul.model.RefreshToken;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface UserRefreshTokenRepo extends JpaRepository<RefreshToken, UUID> {

}
