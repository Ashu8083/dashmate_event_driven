package com.example.smartbite.store.rider_modul.mapper;

import com.example.smartbite.store.rider_modul.DTO.RiderDTO;
import com.example.smartbite.store.rider_modul.DTO.RiderModelReplicaDTO;
import com.example.smartbite.store.rider_modul.internalModule.model.Riders;
import org.springframework.stereotype.Component;

@Component
public class RiderMapper {
    public RiderDTO entityToDTO(Riders rider){

        RiderDTO riderDTO = new RiderDTO();
        riderDTO.setId(rider.getId());
        riderDTO.setUserID(rider.getUserId());
        riderDTO.setAge(rider.getAge());
        riderDTO.setGender(rider.getGender());
        return riderDTO;
    }

    public RiderModelReplicaDTO entityToReplica(Riders rider){

        return new RiderModelReplicaDTO(
                rider.getId(),
                rider.getAge(),
                rider.getGender()
        );
    }

}
