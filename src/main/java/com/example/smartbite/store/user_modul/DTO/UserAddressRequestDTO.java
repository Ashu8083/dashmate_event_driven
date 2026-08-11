package com.example.smartbite.store.user_modul.DTO;


import jakarta.validation.constraints.NegativeOrZero;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserAddressRequestDTO {

    @NotBlank
    private String house_no;
    @NotBlank
    private String street;
    @NotBlank
    private String city;
    @NotBlank
    private String state;
}
