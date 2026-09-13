package com.example.smartbite.store.helper_service;


import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Map;

@Slf4j
@Component
public class ChargeCalculation {

    final private WeatherChecker weatherChecker ;

    public ChargeCalculation(WeatherChecker weatherChecker) {
        this.weatherChecker = weatherChecker;
    }

    public Float chargeCalculate (){
        Map<String, Float> weather= weatherChecker.checkWeather();
        Float weather_value = weather.values().iterator().next();
        Float distance = 100.0f ;
        float charge = distance * weather_value;

        log.info("charge calculate is {}",charge);

        return charge;
    }

}
