package com.example.smartbite.store.trip_modul.internalModule.repo;

import com.example.smartbite.store.trip_modul.DTO.TripStatusUpdate;
import com.example.smartbite.store.trip_modul.internalModule.enums.OrderStatus;
import com.example.smartbite.store.trip_modul.internalModule.model.Trips;
import org.springframework.data.jpa.domain.Specification;

import java.time.Instant;
import java.util.Date;
import java.util.UUID;

public class TripSpecs {
    private TripSpecs(){
    }
    public static Specification<Trips> customerID(UUID customerID) {
            return (root, criteriaQuery, criteriaBuilder)
                    -> criteriaBuilder.equal(root.get("customerID"), customerID);
    }
    public static Specification<Trips> tripID(UUID tripId) {
        return (root, query, builder)
                -> builder.equal(root.get("tripID"), tripId) ;
    }

    public static Specification<Trips> tripStatus(OrderStatus tripStatus) {
        return (root,query,builder) ->
                builder.equal(root.get("tripStatus"), tripStatus);
    }

    public static Specification<Trips> byDate(Instant date) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.equal(root.get("date"), date);
    }

}
