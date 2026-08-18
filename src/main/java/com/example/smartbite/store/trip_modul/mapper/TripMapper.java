package com.example.smartbite.store.trip_modul.mapper;


import com.example.smartbite.store.trip_modul.DTO.CreateDropDTO;
import com.example.smartbite.store.trip_modul.DTO.CreatePickUpDTO;
import com.example.smartbite.store.trip_modul.DTO.PickUpAndDropDTO;
import com.example.smartbite.store.trip_modul.DTO.TripRequest;
import com.example.smartbite.store.trip_modul.enums.StopType;
import com.example.smartbite.store.trip_modul.model.TripStops;
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

    public TripStops createTripStopsPickUpRequestEntity(CreatePickUpDTO pickUpDTO, Trips trip) {
        TripStops tripStops = new TripStops();
        tripStops.setAddress(pickUpDTO.address());
        tripStops.setStop_type(StopType.PICKUP);
        tripStops.setContact_name(pickUpDTO.contact_name());
        tripStops.setContact_name(pickUpDTO.contact_phone());
        tripStops.setTrip(trip);
        tripStops.setLatitude(pickUpDTO.latitude());
        tripStops.setLongitude(pickUpDTO.longitude());

        return tripStops;
    }
    public TripStops createTripStopDropOffRequestEntity(CreateDropDTO dropDTO, Trips trip) {
        TripStops tripStops = new TripStops();
        tripStops.setAddress(dropDTO.address());
        tripStops.setStop_type(StopType.DROP);
        tripStops.setContact_name(dropDTO.contact_name());
        tripStops.setContact_name(dropDTO.contact_phone());
        tripStops.setTrip(trip);
        tripStops.setLatitude(dropDTO.latitude());
        tripStops.setLongitude(dropDTO.longitude());

        return tripStops;
    }






}
