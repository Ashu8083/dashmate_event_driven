package com.example.smartbite.store.trip_modul.internalModule.service.interfaces;

import com.example.smartbite.store.trip_modul.DTO.TripHistoryResponesDTO;
import com.example.smartbite.store.trip_modul.DTO.TripModelDTO;
import com.example.smartbite.store.trip_modul.DTO.TripResponseDTO;
import com.example.smartbite.store.trip_modul.DTO.TripStatusUpdate;
import org.springframework.data.domain.Page;

import java.awt.print.Pageable;
import java.util.UUID;

public interface HistoryTripService {

    Page<TripHistoryResponesDTO> getCustomerTrip(UUID customerId , Pageable pageable);


}
