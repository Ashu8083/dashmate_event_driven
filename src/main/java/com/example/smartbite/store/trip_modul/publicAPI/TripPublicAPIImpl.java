package com.example.smartbite.store.trip_modul.publicAPI;

import com.example.smartbite.store.trip_modul.DTO.TripAssignModelReplica;
import com.example.smartbite.store.trip_modul.DTO.TripModelDTO;
import com.example.smartbite.store.trip_modul.DTO.TripResponseDTO;
import com.example.smartbite.store.trip_modul.DTO.TripStatusUpdate;
import com.example.smartbite.store.trip_modul.internalModule.enums.OrderStatus;
import com.example.smartbite.store.trip_modul.internalModule.enums.StopType;
import com.example.smartbite.store.trip_modul.internalModule.model.TripAssign;
import com.example.smartbite.store.trip_modul.internalModule.model.TripStops;
import com.example.smartbite.store.trip_modul.internalModule.model.Trips;
import com.example.smartbite.store.trip_modul.internalModule.repo.TripAssignRepo;
import com.example.smartbite.store.trip_modul.internalModule.repo.TripRepo;
import com.example.smartbite.store.trip_modul.internalModule.service.interfaces.OrderService;
import com.example.smartbite.store.trip_modul.mapper.TripMapper;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;

@Component
public class TripPublicAPIImpl implements  TripPublicAPI{

    final private TripRepo tripRepo;
    final private TripAssignRepo tripAssignRepo;
    final private TripMapper tripMapper;
    final private OrderService orderService;

    public TripPublicAPIImpl(TripRepo tripRepo,TripAssignRepo tripAssignRepo
                            ,TripMapper tripMapper , OrderService orderService) {
        this.tripRepo = tripRepo;
        this.tripAssignRepo = tripAssignRepo;
        this.orderService = orderService;
        this.tripMapper =  tripMapper;
    }

    @Override
    public TripResponseDTO getTripResponse(UUID tripId) {
        Trips trip =  tripRepo.findById(tripId)
                .orElseThrow(()-> new RuntimeException("Trip not found"));
        List<TripStops> tripStop = trip.getTripStop();
        TripStops tripPickUp = tripStop.stream()
                .filter(stop ->stop.getStopType() == StopType.PICKUP).
                findFirst().orElseThrow(()->new RuntimeException("trip stop not found"));

        TripStops tripDropOff = tripStop.stream()
                .filter(stop ->stop.getStopType() == StopType.DROP)
                .findFirst().orElse(null);
        TripResponseDTO tripResponseDTO =
                tripMapper.createTripResponseDTOOnTripCreate(trip,tripPickUp,tripDropOff);

        return tripResponseDTO;
    }

    @Override
    public TripModelDTO getTripModelDTO(UUID tripId) {
        Trips trip =  tripRepo.findById(tripId)
                .orElseThrow(()-> new RuntimeException("Trip not found"));
        List<TripStops> tripStop = trip.getTripStop();
        TripStops tripPickUp = tripStop.stream()
                .filter(stop ->stop.getStopType() == StopType.PICKUP).
                findFirst().orElseThrow(()->new RuntimeException("trip stop not found"));

        TripStops tripDropOff = tripStop.stream()
                .filter(stop ->stop.getStopType() == StopType.DROP)
                .findFirst().orElse(null);
        TripModelDTO tripModelDTO =
                tripMapper.createTripModelDTO(trip,tripPickUp,tripDropOff);
        return tripModelDTO;
    }
    @Override
    public TripAssignModelReplica getTripAssignToRider(UUID rider_id) {
        tripAssignRepo.findByRiderIdAndStatus(rider_id,OrderStatus.ASSIGNED);
        return null;
    }

    @Override
    @Transactional
    public void updateTripStatus(TripStatusUpdate tripStatusUpdate) {
        orderService.updateOrderStatus(tripStatusUpdate);

    }


}
