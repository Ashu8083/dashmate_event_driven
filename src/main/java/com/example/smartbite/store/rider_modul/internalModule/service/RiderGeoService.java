package com.example.smartbite.store.rider_modul.internalModule.service;

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

        redisTemplate.opsForGeo().add(  // For Geo location redis provide  opsForGeo()  to add geo location
                RIDER_AVAILABLE,
                point,
                riderId.toString()
        );
    }

    public void updateGeoLocation(
            UUID riderId,
            Double longitude,
            Double latitude
    ) {

        Point point = new Point(longitude, latitude);

        redisTemplate.opsForGeo().add(  // For Geo location redis provide  opsForGeo()  to add geo location
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
                        RIDER_AVAILABLE,
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
