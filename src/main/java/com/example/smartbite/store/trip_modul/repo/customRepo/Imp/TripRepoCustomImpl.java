package com.example.smartbite.store.trip_modul.repo.customRepo.Imp;

import com.example.smartbite.store.trip_modul.enums.OrderStatus;
import com.example.smartbite.store.trip_modul.model.Trips;
import com.example.smartbite.store.trip_modul.repo.customRepo.TripRepoCustom;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public class TripRepoCustomImpl implements TripRepoCustom {


    @PersistenceContext
    private EntityManager em;

    public TripRepoCustomImpl(EntityManager em) {
        this.em = em;
    }

    @Override
    public List<Trips> getDeliveredTripByCustomerId(UUID customerId, OrderStatus status) {
        return em.createQuery("""
            SELECT t from Trips t where t.status = :status and t.customerId = :customerId
            """).setParameter("status", status)
                .setParameter("customerId", customerId)
                .getResultList();
    }


}
