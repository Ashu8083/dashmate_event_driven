package com.example.smartbite.store;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@Slf4j
@EnableCaching
@SpringBootApplication
public class StoreApplication {

    public static void main(String[] args) {
        log.info("Starting SmartBite Store application...");
        SpringApplication.run(StoreApplication.class, args);
        log.info("SmartBite Store application started successfully.");
    }


}
