package com.example.smartbite.store.rider_modul.service;


import com.example.smartbite.store.rider_modul.model.Riders;
import com.example.smartbite.store.rider_modul.repo.RiderRepo;
import com.example.smartbite.store.trip_modul.DTO.PickUpAndDropDTO;
import com.example.smartbite.store.trip_modul.model.Trips;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Service;

@Service
public class RiderService {

    private final RiderRepo riderRepo;

    public RiderService(RiderRepo riderRepo){
        this.riderRepo = riderRepo;
    }

    public Riders getAvailableRider ( PickUpAndDropDTO pickUpAddress){
        Riders rider = new Riders();
        rider = riderRepo.findNearestAvailableRider(pickUpAddress.createDropDTO().latitude()
                                                    ,pickUpAddress.createDropDTO().longitude())
                                                    .orElseThrow(()-> new RuntimeException("Rider not found"));
        return rider;
    }



}
