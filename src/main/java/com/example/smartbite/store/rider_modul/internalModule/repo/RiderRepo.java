package com.example.smartbite.store.rider_modul.internalModule.repo;

import com.example.smartbite.store.rider_modul.internalModule.model.Riders;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface RiderRepo extends JpaRepository<Riders, UUID> {


    @Query(value = """
    SELECT *
    FROM riders r
    WHERE r.is_available = true
    ORDER BY (
        6371 * acos(
            cos(radians(:latitude))
            * cos(radians(r.latitude))
            * cos(radians(r.longitude) - radians(:longitude))
            + sin(radians(:latitude))
            * sin(radians(r.latitude))
        )
    )
    LIMIT 1
    """, nativeQuery = true)
    Optional<Riders> findNearestAvailableRider(
            @Param("latitude") double latitude,
            @Param("longitude") double longitude
    );

}
