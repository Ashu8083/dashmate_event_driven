package com.example.smartbite.store.common.element;

public record APIResponse<T>(
        boolean success,
        String message,
        T data

) {
}
