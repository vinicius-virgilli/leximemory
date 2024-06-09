package com.leximemory.backend.controllers.dto;

public record ResponseDto<T>(String message, T data) {}
