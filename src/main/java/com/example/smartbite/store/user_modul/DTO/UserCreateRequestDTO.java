package com.example.smartbite.store.user_modul.DTO;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserCreateRequestDTO {

    @NotBlank
    public  String name;
    @NotBlank
    @Email
    public String email;
    @NotBlank
    public String number;
}



