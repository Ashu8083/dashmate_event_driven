package com.example.smartbite.store.user_modul.internalModule.repo;

import com.example.smartbite.store.user_modul.internalModule.model.UserDevice;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface UserDeviceRepo extends JpaRepository<UserDevice, UUID> {



}
