package com.example.smartbite.store;


import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController()
@RequestMapping("/test")
public class TestController {

    @GetMapping("/test-hello")

    public Map<String, String> testMessage() {
        Map<String, String> response = new HashMap<>();
        response.put("message", "hello");
        return response;
    }
}
