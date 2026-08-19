package com.example.smartbite.store.trip_modul.mapper;


import com.example.smartbite.store.rider_modul.model.Riders;
import com.example.smartbite.store.trip_modul.DTO.*;
import com.example.smartbite.store.trip_modul.enums.OrderStatus;
import com.example.smartbite.store.trip_modul.enums.StopType;
import com.example.smartbite.store.trip_modul.model.TripStops;
import com.example.smartbite.store.trip_modul.model.Trips;
import com.example.smartbite.store.trip_modul.repo.TripStop;
import com.example.smartbite.store.user_modul.model.Users;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class TripMapper {


    public Trips createTripRequestEntity (TripRequest tripRequest, Users user) {

        Trips trips = new Trips();
        trips.setCustomer(user);
        trips.setPackage_description(tripRequest.getPackage_description());
        trips.setScheduled_at(tripRequest.getScheduled_time());
        return trips;
    }
    public TripResponseDTO createTripResponseDTOOnTripCreate(Trips trip, TripStops pickUpAddress, TripStops dropOffAddress) {
        TripResponseDTO tripResponseDTO = new TripResponseDTO();

        // map use to store the location of user
        Map<String,Double> pickuplocation = new HashMap<>();
        Map<String,Double> dropOfflocation = new HashMap<>();
        pickuplocation.put("latitude",pickUpAddress.getLatitude());
        pickuplocation.put("longitude",pickUpAddress.getLongitude());
        dropOfflocation.put("latitude",dropOffAddress.getLatitude());
        dropOfflocation.put("longitude",dropOffAddress.getLongitude());

        //Assign Value to the DTO
        tripResponseDTO.setTripId(trip.getId());
        tripResponseDTO.setCustomerId(trip.getCustomer().getId());
        tripResponseDTO.setPackage_description(trip.getPackage_description());
        tripResponseDTO.setPickUpAddress(pickUpAddress.getAddress());
        tripResponseDTO.setPickUpLocations(pickuplocation);
        tripResponseDTO.setDropOffLocations(dropOfflocation);
        tripResponseDTO.setDropOffAddress(dropOffAddress.getAddress());

        //Set rider info null for the first time
        tripResponseDTO.setRiderId(null);
        tripResponseDTO.setRiderName(null);
        tripResponseDTO.setRider_gander(null);
        tripResponseDTO.setOrderStatus(OrderStatus.PENDING);

        return tripResponseDTO;



    }

    public TripResponseDTO updateTripResponseDTOOnTripUpdate(TripResponseDTO tripResponseDTO, Riders rider) {

        tripResponseDTO.setRiderId(rider.getId());
        tripResponseDTO.setRider_gander(rider.getGender());
        tripResponseDTO.setRiderName(rider.getUser().getName());
        tripResponseDTO.setOrderStatus(OrderStatus.ASSIGNED);
        return tripResponseDTO;

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
