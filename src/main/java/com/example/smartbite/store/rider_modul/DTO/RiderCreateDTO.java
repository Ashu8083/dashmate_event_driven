package com.example.smartbite.store.rider_modul.DTO;

import com.example.smartbite.store.rider_modul.enums.Gender;
import com.example.smartbite.store.user_modul.model.Users;

public record RiderCreateDTO (
         Users user,
         Integer age,
         Gender gender
){ }
