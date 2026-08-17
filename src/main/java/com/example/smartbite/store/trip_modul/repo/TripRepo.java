package com.example.smartbite.store.trip_modul.repo;

import com.example.smartbite.store.trip_modul.enums.OrderStatus;
import com.example.smartbite.store.trip_modul.model.Trips;
import com.example.smartbite.store.trip_modul.repo.customRepo.TripRepoCustom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface TripRepo extends JpaRepository<Trips, UUID> , TripRepoCustom {

    Optional<Trips> findByCustomerId(UUID customerId);

    Optional<Trips> getByid (UUID id);

    List<Trips> getAllByCustomerId(UUID customerId);

    Optional<Trips> getByidAndStatus(UUID id , OrderStatus status);


}
