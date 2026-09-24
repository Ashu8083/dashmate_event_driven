package com.example.smartbite.store.trip_modul.mapper;

import com.example.smartbite.store.kafaka.events.TripCreateEvent;
import com.example.smartbite.store.rider_modul.DTO.RiderDTO;
import com.example.smartbite.store.rider_modul.internalModule.enums.Gender;
import com.example.smartbite.store.trip_modul.DTO.*;
import com.example.smartbite.store.trip_modul.internalModule.enums.OrderStatus;
import com.example.smartbite.store.trip_modul.internalModule.enums.StopType;
import com.example.smartbite.store.trip_modul.internalModule.model.TripStops;
import com.example.smartbite.store.trip_modul.internalModule.model.Trips;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
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
        tripResponseDTO.setPackage_description(trip.getPackage_description());
        tripResponseDTO.setPickUpAddress(pickUpAddress.getAddress());
        tripResponseDTO.setPickUpLocations(pickuplocation);
        tripResponseDTO.setDropOffLocations(dropOfflocation);
        tripResponseDTO.setDropOffAddress(dropOffAddress.getAddress());

        //Set rider info null for the first time

        tripResponseDTO.setOrderStatus(OrderStatus.PENDING);

        return tripResponseDTO;
    }

    public TripResponseDTO createTripResponseDTOOnTripAssign(Trips trip, TripStops pickUpAddress,
                                                             TripStops dropOffAddress) {


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
        tripResponseDTO.setPackage_description(trip.getPackage_description());
        tripResponseDTO.setPickUpAddress(pickUpAddress.getAddress());
        tripResponseDTO.setPickUpLocations(pickuplocation);
        tripResponseDTO.setDropOffLocations(dropOfflocation);
        tripResponseDTO.setDropOffAddress(dropOffAddress.getAddress());

        //Set rider info null for the first time
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

    public RiderAssigned createRiderAssignedDTO(String riderName, Gender rider_gender , Integer rider_age ){
        RiderAssigned riderAssigned = new RiderAssigned();
        if(rider_age == null){
            rider_age = 0;
        }
        riderAssigned.setAge(rider_age);
        riderAssigned.setGender(rider_gender);
        riderAssigned.setRiderName(riderName);
        return riderAssigned;
    }


    public TripCreateEvent createTripCreateEventDTO(Trips trip, PickUpAndDropDTO pickUpAndDropDTO ) {
        return  new TripCreateEvent(
                trip.getPackage_description(),
                pickUpAndDropDTO,
                trip.getId(),
                120.0f

        );
    }

    public TripHistoryResponesDTO convertToResponseDTO(Trips  trip){
        List<TripStops> stops = trip.getTripStop();

        TripStops pickup = stops.stream()
                .filter(stop -> stop.getStopType() == StopType.PICKUP)
                .findFirst()
                .orElse(null);

        TripStops drop = stops.stream()
                .filter(stop -> stop.getStopType() == StopType.DROP)
                .findFirst()
                .orElse(null);

        CreatePickUpDTO createPickUpDTO =
                new CreatePickUpDTO(
                        pickup.getStopType(),
                        pickup.getAddress(),
                        pickup.getLatitude(),
                        pickup.getLongitude(),
                        pickup.getContact_name(),
                        pickup.getContact_phone()
                );

        CreateDropDTO createDropDTO =
                new CreateDropDTO(
                        drop.getStopType(),
                        drop.getAddress(),
                        drop.getLatitude(),
                        drop.getLongitude(),
                        drop.getContact_name(),
                        drop.getContact_phone()
                );


        PickUpAndDropDTO pickUpAndDropDTO =
                new PickUpAndDropDTO(
                            createDropDTO,
                            createPickUpDTO
                         );

        return new TripHistoryResponesDTO(
                trip.getId(),
                trip.getTripAssign().getRiderId(),
                trip.getStatus(),
                pickUpAndDropDTO,
                trip.getCreated_at()
        );

    }

}
