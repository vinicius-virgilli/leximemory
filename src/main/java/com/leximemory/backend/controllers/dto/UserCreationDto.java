package com.leximemory.backend.controllers.dto;

import java.sql.Timestamp;

public record UserCreationDto(String name, String email, String password, Timestamp registrationDate) {}
