package com.example.smartbite.store.trip_modul.repo;

import com.example.smartbite.store.trip_modul.model.TripAssign;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface TripAssignRepo extends   JpaRepository<TripAssign, UUID> {
}
