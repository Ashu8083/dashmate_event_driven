package com.example.smartbite.store.rider_modul.DTO;

import com.example.smartbite.store.rider_modul.internalModule.enums.Gender;
import com.example.smartbite.store.user_modul.internalModule.model.Users;

public record RiderCreateDTO (
         Users user,
         Integer age,
         Gender gender
){ }
