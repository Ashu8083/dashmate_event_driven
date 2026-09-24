package com.example.smartbite.store.config.webScoket;

import com.fasterxml.jackson.databind.JsonNode;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class WebSocketRequest {

    private String type;
    private JsonNode  payload;

}
