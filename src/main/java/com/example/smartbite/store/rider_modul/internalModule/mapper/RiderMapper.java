package com.example.smartbite.store.rider_modul.internalModule.mapper;

import com.example.smartbite.store.rider_modul.internalModule.DTO.RiderDTO;
import com.example.smartbite.store.rider_modul.internalModule.model.Riders;
import org.springframework.stereotype.Component;

@Component
public class RiderMapper {
    public RiderDTO entityToDTO(Riders rider){

        RiderDTO riderDTO = new RiderDTO();
        riderDTO.setId(rider.getId());
        riderDTO.setAge(rider.getAge());
        riderDTO.setGender(rider.getGender());
        return riderDTO;
    }
}
