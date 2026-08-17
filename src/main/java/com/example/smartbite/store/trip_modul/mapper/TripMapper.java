package com.example.smartbite.store.trip_modul.mapper;


import com.example.smartbite.store.trip_modul.DTO.TripRequest;
import com.example.smartbite.store.trip_modul.model.Trips;
import com.example.smartbite.store.user_modul.model.Users;
import org.springframework.stereotype.Component;

@Component
public class TripMapper {


    public Trips createTripRequestEntity (TripRequest tripRequest, Users user) {

        Trips trips = new Trips();
        trips.setCustomer(user);
        trips.setPackage_description(tripRequest.getPackage_description());
        trips.setScheduled_at(tripRequest.getScheduled_time());
        return trips;
    }






}
