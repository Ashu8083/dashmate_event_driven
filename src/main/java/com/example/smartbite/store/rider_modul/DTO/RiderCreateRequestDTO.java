package com.example.smartbite.store.rider_modul.DTO;

import com.example.smartbite.store.rider_modul.internalModule.enums.Gender;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;

public record RiderCreateRequestDTO(

        @NotNull String name,
        @Email String email,
        @NotNull String Number,
        Integer age,
        Gender gender
) {
}
