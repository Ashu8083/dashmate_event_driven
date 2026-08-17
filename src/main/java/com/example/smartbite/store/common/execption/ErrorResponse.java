package com.example.smartbite.store.common.execption;

import java.time.LocalDateTime;

public record ErrorResponse (
        int status,
        String message,
        String path,
        LocalDateTime timestamp
){

}
