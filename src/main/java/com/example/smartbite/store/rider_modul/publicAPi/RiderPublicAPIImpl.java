package com.example.smartbite.store.rider_modul.publicAPi;

import com.example.smartbite.store.common.execption.ResourceNotFoundException;
import com.example.smartbite.store.rider_modul.DTO.RiderModelReplicaDTO;
import com.example.smartbite.store.rider_modul.internalModule.model.Riders;
import com.example.smartbite.store.rider_modul.internalModule.repo.RiderRepo;
import com.example.smartbite.store.rider_modul.internalModule.service.RiderService;
import com.example.smartbite.store.rider_modul.mapper.RiderMapper;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class RiderPublicAPIImpl implements RiderPublicAPI {

    private final RiderRepo riderRepo;
    private final RiderMapper riderMapper;


    public RiderPublicAPIImpl(RiderRepo riderRepo,
                              RiderMapper riderMapper) {
        this.riderRepo = riderRepo;
        this.riderMapper = riderMapper;
    }
    @Override
    public RiderModelReplicaDTO getRiderReplica(UUID riderId) {
        Riders rider = riderRepo.findById(riderId).orElseThrow(
                () -> new  ResourceNotFoundException("Account not Found with this rider id : " + riderId + " not found")
        );
        RiderModelReplicaDTO riderModelReplicaDTO = riderMapper.entityToReplica(rider);
        return riderModelReplicaDTO;
    }
}
