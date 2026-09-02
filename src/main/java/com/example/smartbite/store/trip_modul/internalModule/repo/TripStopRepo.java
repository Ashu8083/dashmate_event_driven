package com.example.smartbite.store.trip_modul.internalModule.repo;

import com.example.smartbite.store.trip_modul.internalModule.enums.StopType;
import com.example.smartbite.store.trip_modul.internalModule.model.TripStops;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;


@Repository
public interface TripStopRepo
        extends JpaRepository<TripStops, UUID> {

    Optional<TripStops> findByTripIdAndStopType(UUID tripId, StopType stopType);

}
