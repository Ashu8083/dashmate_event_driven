package com.example.smartbite.store.trip_modul.DTO;

import com.example.smartbite.store.rider_modul.internalModule.enums.Gender;
import com.example.smartbite.store.rider_modul.internalModule.model.Riders;
import com.example.smartbite.store.trip_modul.internalModule.model.Trips;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class RiderAssigned{

     private String riderName;
     private Gender gender;
     private Integer age;


}