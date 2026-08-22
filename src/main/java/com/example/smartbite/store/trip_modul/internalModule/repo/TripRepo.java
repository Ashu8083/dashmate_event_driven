package com.example.smartbite.store.trip_modul.internalModule.repo;

import com.example.smartbite.store.trip_modul.internalModule.enums.OrderStatus;
import com.example.smartbite.store.trip_modul.internalModule.model.Trips;
import com.example.smartbite.store.trip_modul.internalModule.repo.customRepo.TripRepoCustom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface TripRepo extends JpaRepository<Trips, UUID> , TripRepoCustom {

    @Query("""
    SELECT DISTINCT t
    FROM Trips t
    LEFT JOIN FETCH t.tripAssign
    LEFT JOIN FETCH t.tripStop
    WHERE t.customerId = :customerId
""")
    Optional<Trips> findByCustomerId(@Param("customerId") UUID customerId);

    @Query(
            """
            SELECT DISTINCT t 
            FROM Trips t 
            LEFT JOIN FETCH t.tripAssign 
            LEFT JOIN FETCH t.tripStop 
            WHERE t = :tripId
            """
    )
    Optional<Trips> findByTripId(@Param("tripId") UUID tripId);

    List<Trips> getAllByCustomerId(UUID customerId);
    Optional<Trips> getByIdAndStatus(UUID id , OrderStatus status);
}
