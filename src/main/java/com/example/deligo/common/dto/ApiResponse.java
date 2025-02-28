package com.example.deligo.common.dto;

import lombok.Getter;

@Getter
public class ApiResponse {

    private String message;

    public ApiResponse(String message) {
        this.message = message;
    }
}
