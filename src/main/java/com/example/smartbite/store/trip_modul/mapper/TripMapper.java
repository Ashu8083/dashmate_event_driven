package com.example.smartbite.store.trip_modul.mapper;


import com.example.smartbite.store.rider_modul.DTO.RiderDTO;
import com.example.smartbite.store.rider_modul.DTO.RiderModelReplicaDTO;
import com.example.smartbite.store.trip_modul.DTO.*;
import com.example.smartbite.store.trip_modul.internalModule.enums.OrderStatus;
import com.example.smartbite.store.trip_modul.internalModule.enums.StopType;
import com.example.smartbite.store.trip_modul.internalModule.model.TripStops;
import com.example.smartbite.store.trip_modul.internalModule.model.Trips;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Component
public class TripMapper {


    public Trips createTripRequestEntity (TripRequest tripRequest, UUID user_id) {

        Trips trips = new Trips();
        trips.setCustomerId(user_id);
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
        tripResponseDTO.setCustomerId(trip.getCustomerId());
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

    public TripResponseDTO createTripResponseDTOOnTripAssign(Trips trip, TripStops pickUpAddress,
                                                             TripStops dropOffAddress, RiderModelReplicaDTO riderDTO, String riderName) {


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
        tripResponseDTO.setCustomerId(trip.getCustomerId());
        tripResponseDTO.setPackage_description(trip.getPackage_description());
        tripResponseDTO.setPickUpAddress(pickUpAddress.getAddress());
        tripResponseDTO.setPickUpLocations(pickuplocation);
        tripResponseDTO.setDropOffLocations(dropOfflocation);
        tripResponseDTO.setDropOffAddress(dropOffAddress.getAddress());

        //Set rider info null for the first time
        tripResponseDTO.setRiderId(riderDTO.rider_id());
        tripResponseDTO.setRiderName(riderName);
        tripResponseDTO.setRider_gander(riderDTO.gander());
        tripResponseDTO.setOrderStatus(OrderStatus.ASSIGNED);

        return  tripResponseDTO;


    }

    public TripModelDTO createTripModelDTO(Trips trip, TripStops pickUpAddress, TripStops dropOffAddress) {

        TripModelDTO tripModelDTO = new TripModelDTO();

        Map<String,Double> pickuplocation = new HashMap<>();
        Map<String,Double> dropOfflocation = new HashMap<>();
        pickuplocation.put("latitude",pickUpAddress.getLatitude());
        pickuplocation.put("longitude",pickUpAddress.getLongitude());
        dropOfflocation.put("latitude",dropOffAddress.getLatitude());
        dropOfflocation.put("longitude",dropOffAddress.getLongitude());

        //Assign Value to the DTO
        tripModelDTO.setTripId(trip.getId());
        tripModelDTO.setCustomerId(trip.getCustomerId());
        tripModelDTO.setPackage_description(trip.getPackage_description());
        tripModelDTO.setPickUpAddress(pickUpAddress.getAddress());
        tripModelDTO.setPickUpLocations(pickuplocation);
        tripModelDTO.setDropOffLocations(dropOfflocation);
        tripModelDTO.setDropOffAddress(dropOffAddress.getAddress());

        //Set rider info null for the first time
        tripModelDTO.setRiderId(null);
        tripModelDTO.setRiderName(null);
        tripModelDTO.setRider_gander(null);
        tripModelDTO.setOrderStatus(OrderStatus.PENDING);

        return tripModelDTO;

    }

    public TripResponseDTO updateTripResponseDTOOnTripUpdate(TripResponseDTO tripResponseDTO, RiderDTO rider) {

        tripResponseDTO.setRiderId(rider.getId());
        tripResponseDTO.setRider_gander(rider.getGender());
        tripResponseDTO.setOrderStatus(OrderStatus.ASSIGNED);
        return tripResponseDTO;

    }

    public TripStops createTripStopsPickUpRequestEntity(CreatePickUpDTO pickUpDTO, Trips trip) {
        TripStops tripStops = new TripStops();
        tripStops.setAddress(pickUpDTO.address());
        tripStops.setStopType(StopType.PICKUP);
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
        tripStops.setStopType(StopType.DROP);
        tripStops.setContact_name(dropDTO.contact_name());
        tripStops.setContact_name(dropDTO.contact_phone());
        tripStops.setTrip(trip);
        tripStops.setLatitude(dropDTO.latitude());
        tripStops.setLongitude(dropDTO.longitude());

        return tripStops;
    }






}
