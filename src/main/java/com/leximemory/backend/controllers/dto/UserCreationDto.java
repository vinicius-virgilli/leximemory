package com.leximemory.backend.controllers.dto;

import java.sql.Timestamp;

/**
 * The type User creation dto.
 */
public record UserCreationDto(
    String name,
    String email,
    String password,
    Timestamp registrationDate
) {}
