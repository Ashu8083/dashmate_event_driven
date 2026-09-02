package com.example.smartbite.store.rider_modul.DTO;

import com.example.smartbite.store.rider_modul.internalModule.enums.Gender;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class RiderDTO {
    public UUID id ;
    public UUID userID;
    public Integer age;
    public Gender gender;
}
