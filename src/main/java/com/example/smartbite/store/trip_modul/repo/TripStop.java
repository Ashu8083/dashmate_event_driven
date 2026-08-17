package com.example.smartbite.store.trip_modul.repo;

import com.example.smartbite.store.trip_modul.model.TripStops;
import com.example.smartbite.store.trip_modul.model.Trips;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;


@Repository
public interface TripStop
        extends JpaRepository<TripStops, UUID> {

}
