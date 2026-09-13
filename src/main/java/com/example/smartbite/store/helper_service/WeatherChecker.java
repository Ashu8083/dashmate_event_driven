package com.example.smartbite.store.helper_service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;


@Slf4j
@Component
public class WeatherChecker {
    public Map<String,Float> checkWeather(){

       List<Map<String,Float>> weather = new ArrayList<>();
        weather.add(Map.of("Rainy", 25.5f));
        weather.add(Map.of("Sunny", 32.0f));
        weather.add(Map.of("Cloudy", 28.5f));

        Map<String,Float> random_weather  = weather.get(new Random().nextInt(weather.size()));

        log.info("Weather check result:{}",random_weather);
        return random_weather;
    }
}
