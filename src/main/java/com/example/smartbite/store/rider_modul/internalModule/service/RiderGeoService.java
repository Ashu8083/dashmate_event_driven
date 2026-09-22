package com.example.smartbite.store.rider_modul.internalModule.service;

import com.example.smartbite.store.common.execption.ResourceNotFoundException;
import com.example.smartbite.store.rider_modul.DTO.RiderDTOOnActive;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.geo.*;
import org.springframework.data.redis.connection.RedisGeoCommands;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;


@Service
@Slf4j
@RequiredArgsConstructor
public class RiderGeoService {

    private final RedisTemplate<String, Object> redisTemplate;
    private final String LOCATION_UPDATE = "rider:locations";
    private final String RIDER_AVAILABLE = "rider:available";

    public void updateGeoLocationOnRideAvailable(
            UUID riderId,
            Double longitude,
            Double latitude
    ) {

        Point point = new Point(longitude, latitude);
        redisTemplate.opsForSet().add(RIDER_AVAILABLE, riderId);

        redisTemplate.opsForGeo().add(  // For Geo location redis provide  opsForGeo()  to add geolocation
                LOCATION_UPDATE,
                point,
                riderId.toString()
        );
    }

    public Boolean findRiderIsAvailableOrNot(UUID riderId) {
        Boolean riderExit = redisTemplate.opsForSet().isMember(RIDER_AVAILABLE, riderId);
        if (!Boolean.TRUE.equals(riderExit)) {
            throw new ResourceNotFoundException("Rider currently not available");
        }
        return Boolean.TRUE;
    }

    public UUID markRiderAsAvailable(UUID riderId) {
        if(Boolean.FALSE.equals(redisTemplate.opsForSet().isMember(RIDER_AVAILABLE, riderId))) {
            redisTemplate.opsForSet().add(RIDER_AVAILABLE, riderId);
            return  riderId;
        }
        else  {
            throw new ResourceNotFoundException("Rider Details Can't Update");
        }
    }

    public void removeRiderIsAvailable(UUID riderId) {
        redisTemplate.opsForSet().remove(RIDER_AVAILABLE, riderId);
    }

    public void updateGeoLocation(
            UUID riderId,
            Double longitude,
            Double latitude
    ) {

        Point point = new Point(longitude, latitude);

        redisTemplate.opsForGeo().add(  // For Geo location redis provide  opsForGeo()  to add geolocation
                LOCATION_UPDATE,
                point,
                riderId.toString()
        );
    }

    public List<String> findNearbyRiders(
            double latitude,
            double longitude,
            double radiusKm
    ) {

        Circle circle = new Circle(
                new Point(longitude, latitude),
                new Distance(radiusKm, Metrics.KILOMETERS)
        );

        GeoResults<RedisGeoCommands.GeoLocation<Object>> results =
                redisTemplate.opsForGeo().radius(
                        LOCATION_UPDATE,
                        circle
                );

        return results.getContent()
                .stream()
                .map(result ->
                        result.getContent()
                                .getName()
                                .toString()
                )
                .toList();
    }

}
