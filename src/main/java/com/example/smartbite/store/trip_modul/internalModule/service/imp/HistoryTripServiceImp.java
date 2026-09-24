package com.example.smartbite.store.trip_modul.internalModule.service.imp;

import com.example.smartbite.store.trip_modul.DTO.TripHistoryResponesDTO;
import com.example.smartbite.store.trip_modul.DTO.TripResponseDTO;
import com.example.smartbite.store.trip_modul.internalModule.model.Trips;
import com.example.smartbite.store.trip_modul.internalModule.repo.TripRepo;
import com.example.smartbite.store.trip_modul.internalModule.service.interfaces.HistoryTripService;
import com.example.smartbite.store.trip_modul.mapper.TripMapper;
import com.example.smartbite.store.user_modul.publicApi.UserModuleApi;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.stereotype.Service;

import java.awt.print.Pageable;
import java.util.UUID;


@Service
public class HistoryTripServiceImp implements HistoryTripService {

    final private  TripRepo tripRepo;
    final private UserModuleApi userModuleApi;
    final private TripMapper tripMapper;

    public HistoryTripServiceImp(TripRepo tripRepo , UserModuleApi userModuleApi,TripMapper tripMapper) {
        this.tripRepo = tripRepo;
        this.userModuleApi = userModuleApi  ;
        this.tripMapper = tripMapper;
    }

    @Override
    public Page<TripHistoryResponesDTO> getCustomerTrip(UUID customerId, Pageable pageable) {

        userModuleApi.getUserModuleReplica(customerId);

        Page<Trips> customerTripHistory =
                tripRepo.findByCustomerId(customerId, pageable);
        Page<TripHistoryResponesDTO> tripResponseDTOPage =
                customerTripHistory.map(
                        trip -> tripMapper.convertToResponseDTO(trip));
        return tripResponseDTOPage;
    }
}
