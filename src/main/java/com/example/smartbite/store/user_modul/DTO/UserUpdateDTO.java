package com.example.smartbite.store.user_modul.DTO;

import jakarta.validation.constraints.NotBlank;

public class UserUpdateDTO{

    @NotBlank
    String email;

    String number;

    
}
