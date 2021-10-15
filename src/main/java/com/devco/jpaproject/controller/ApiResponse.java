package com.devco.jpaproject.controller;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class ApiResponse<T>{
    private int statusCode;
    private T data;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
    private LocalDateTime serverDateTime;

    private ApiResponse(int statusCode, T data) {
        this.statusCode = statusCode;
        this.data = data;
        this.serverDateTime = LocalDateTime.now();
    }

    public static <T> ApiResponse<T> ok(T data){
        return new ApiResponse<>(200, data);
    }

    public static <T> ApiResponse<T> created(T data){
        return new ApiResponse<>(201, data);
    }

    public static <T> ApiResponse<T> fail(int statusCode, T data){
        return new ApiResponse<>(statusCode, data);
    }
}

