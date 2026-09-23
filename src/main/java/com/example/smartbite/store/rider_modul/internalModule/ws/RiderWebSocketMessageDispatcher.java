package com.example.smartbite.store.rider_modul.internalModule.ws;


import com.example.smartbite.store.config.WebSocketRequest;
import com.example.smartbite.store.rider_modul.DTO.RiderAvailableDTO;
import com.example.smartbite.store.rider_modul.DTO.RiderTripAccept;
import com.example.smartbite.store.rider_modul.internalModule.service.RiderGeoService;
import com.example.smartbite.store.rider_modul.internalModule.service.RiderService;
import com.example.smartbite.store.rider_modul.internalModule.service.TripRequestService;
import com.example.smartbite.store.rider_modul.mapper.RiderMapper;
import com.example.smartbite.store.rider_modul.publicAPi.RiderPublicAPI;
import com.example.smartbite.store.trip_modul.DTO.TripStatusUpdate;
import com.example.smartbite.store.trip_modul.publicAPI.TripPublicAPI;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

import java.io.IOException;
import java.util.UUID;


@Slf4j
@Component
public class RiderWebSocketMessageDispatcher {

    private final ObjectMapper objectMapper;
    private final RiderService riderService;
    private final RiderGeoService riderGeoService;
    private final TripPublicAPI tripPublicAPI;
    private final TripRequestService tripRequestService;
    public RiderWebSocketMessageDispatcher(RiderService riderService ,
                                           ObjectMapper objectMapper,
                                           RiderGeoService riderGeoService,
                                           TripPublicAPI tripPublicAPI,
                                           TripRequestService tripRequestService) {
        this.objectMapper = objectMapper;
        this.riderService = riderService;
        this.riderGeoService = riderGeoService;
        this.tripPublicAPI = tripPublicAPI;
        this.tripRequestService = tripRequestService;
    }

    public void dispatch(WebSocketSession session,
                         WebSocketRequest request) throws IOException {
            UUID riderId =(UUID) session.getAttributes().get("userId");
            switch (request.getType()) {

                case "LOCATION_UPDATE":
                    log.info("Received location update request");

                    RiderAvailableDTO riderAvailableDTO = objectMapper.convertValue(request.getPayload(), RiderAvailableDTO.class);
                    riderGeoService.updateGeoLocation(riderId,
                                                        riderAvailableDTO.longitude(),
                                                        riderAvailableDTO.latitude());
                    log.info("rider location updated");
                    break;

                case "ACCEPT_TRIP":
                    log.info("Received accept trip request");
                    RiderTripAccept riderTripAccept = objectMapper.convertValue(request.getPayload()
                                                      ,RiderTripAccept.class );

                    tripRequestService.tripAccept(riderId,riderTripAccept.tripId());
                    break;

                case "RIDER_STATUS_UPDATE":
                    RiderAvailableDTO dto = objectMapper.convertValue(request.getPayload()
                                    , RiderAvailableDTO.class);
                    riderService.makeActiveRiderAndInactive(riderId,dto);
                    log.info("Received rider update request");
                    break;

                case "OUT_FOR_DELIVERY" :
                    log.info("Received out for delivery request for package hand over or out for delivery");
                    TripStatusUpdate tripStatusUpdateDTO = objectMapper.convertValue(request.getPayload(),
                                                                    TripStatusUpdate.class);
                    tripPublicAPI.updateTripStatus(tripStatusUpdateDTO);
                    break;

                case "PACKAGE_DELEVERED":
                    log.info("Received package DELEVERED request");
                    TripStatusUpdate tripStatusUpdate = objectMapper.convertValue(request.getPayload(),TripStatusUpdate.class);
                    tripPublicAPI.updateTripStatus(tripStatusUpdate);
                    riderGeoService.markRiderAsAvailable(riderId);
                     break;

                default:

                    String message = "invalid request";
                    session.sendMessage(new TextMessage(message));
                    throw new IllegalArgumentException(
                            "Unknown WebSocket type: " + request.getType());
            }


    }


}
