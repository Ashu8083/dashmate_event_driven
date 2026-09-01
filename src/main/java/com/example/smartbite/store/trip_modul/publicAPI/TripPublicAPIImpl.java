package com.example.smartbite.store.trip_modul.publicAPI;

import com.example.smartbite.store.trip_modul.DTO.TripModelDTO;
import com.example.smartbite.store.trip_modul.DTO.TripResponseDTO;
import com.example.smartbite.store.trip_modul.internalModule.enums.StopType;
import com.example.smartbite.store.trip_modul.internalModule.model.TripStops;
import com.example.smartbite.store.trip_modul.internalModule.model.Trips;
import com.example.smartbite.store.trip_modul.internalModule.repo.TripRepo;
import com.example.smartbite.store.trip_modul.mapper.TripMapper;

import java.util.List;
import java.util.UUID;

public class TripPublicAPIImpl implements  TripPublicAPI{

    final private TripRepo tripRepo;
    final private TripMapper tripMapper;

    public TripPublicAPIImpl(TripRepo tripRepo) {
        this.tripRepo = tripRepo;
        this.tripMapper = new TripMapper();
    }

    @Override
    public TripResponseDTO getTripResponse(UUID tripId) {
        Trips trip =  tripRepo.findById(tripId)
                .orElseThrow(()-> new RuntimeException("Trip not found"));
        List<TripStops> tripStop = trip.getTripStop();
        TripStops tripPickUp = tripStop.stream()
                .filter(stop ->stop.getStopType() == StopType.PICKUP).
                findFirst().orElseThrow(()->new RuntimeException("trip stop not found"));

        TripStops tripDropOff = tripStop.stream()
                .filter(stop ->stop.getStopType() == StopType.DROP)
                .findFirst().orElse(null);
        TripResponseDTO tripResponseDTO =
                tripMapper.createTripResponseDTOOnTripCreate(trip,tripPickUp,tripDropOff);

        return tripResponseDTO;
    }

    @Override
    public TripModelDTO getTripModelDTO(UUID tripId) {
        Trips trip =  tripRepo.findById(tripId)
                .orElseThrow(()-> new RuntimeException("Trip not found"));
        List<TripStops> tripStop = trip.getTripStop();
        TripStops tripPickUp = tripStop.stream()
                .filter(stop ->stop.getStopType() == StopType.PICKUP).
                findFirst().orElseThrow(()->new RuntimeException("trip stop not found"));

        TripStops tripDropOff = tripStop.stream()
                .filter(stop ->stop.getStopType() == StopType.DROP)
                .findFirst().orElse(null);
        TripModelDTO tripModelDTO =
                tripMapper.createTripModelDTO(trip,tripPickUp,tripDropOff);

        return tripModelDTO;
    }
}
