package com.example.smartbite.store.rider_modul.internalModule.ws;


import com.example.smartbite.store.config.WebSocketRequest;
import com.example.smartbite.store.rider_modul.DTO.RiderAvailableDTO;
import com.example.smartbite.store.rider_modul.DTO.RiderTripAccept;
import com.example.smartbite.store.rider_modul.internalModule.service.RiderService;
import com.example.smartbite.store.rider_modul.internalModule.service.TripRequestService;
import com.example.smartbite.store.rider_modul.mapper.RiderMapper;
import com.example.smartbite.store.trip_modul.DTO.TripStatusUpdate;
import com.example.smartbite.store.trip_modul.publicAPI.TripPublicAPI;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

import java.util.UUID;


@Slf4j
@Component
public class RiderWebSocketMessageDispatcher {

    private final ObjectMapper objectMapper;
    private final RiderService riderService;
    private final TripPublicAPI tripPublicAPI;
    private final TripRequestService tripRequestService;
    public RiderWebSocketMessageDispatcher(RiderService riderService ,
                                           ObjectMapper objectMapper,
                                           TripPublicAPI tripPublicAPI,
                                           TripRequestService tripRequestService) {
        this.objectMapper = objectMapper;
        this.riderService = riderService;
        this.tripPublicAPI = tripPublicAPI;
        this.tripRequestService = tripRequestService;
    }

    public void dispatch(WebSocketSession session,
                         WebSocketRequest request) {
            switch (request.getType()) {

                case "LOCATION_UPDATE":
                    log.info("Received location update request");
                    break;

                case "ACCEPT_TRIP":
                    log.info("Received accept trip request");
                    RiderTripAccept riderTripAccept = objectMapper.convertValue(request.getPayload()
                                                      ,RiderTripAccept.class );
                    tripRequestService.tripAccept(riderTripAccept.riderId(),  riderTripAccept.tripId());
                    break;

                case "RIDER_STATUS_UPDATE":
                    RiderAvailableDTO dto = objectMapper.convertValue(request.getPayload()
                                    , RiderAvailableDTO.class);
                    riderService.makeActiveRiderAndInactive(dto);
                    log.info("Received rider update request");
                    break;

                case "OUT_FOR_DELIVERY" :
                    log.info("Received out for delivery request for pageke hand over or out for delivery");
                    TripStatusUpdate tripStatusUpdateDTO = objectMapper.convertValue(request.getPayload(),
                                                                    TripStatusUpdate.class);
                    tripPublicAPI.updateTripStatus(tripStatusUpdateDTO);
                    break;

                case "PACKAGE_DELEVERED":
                    log.info("Received package DELEVERED request");
                    TripStatusUpdate tripStatusUpdate = objectMapper.convertValue(request.getPayload(),TripStatusUpdate.class);
                    tripPublicAPI.updateTripStatus(tripStatusUpdate);
                     break;

                default:

                    String message = "invalid request";
                    throw new IllegalArgumentException(
                            "Unknown WebSocket type: " + request.getType());
            }


    }


}
