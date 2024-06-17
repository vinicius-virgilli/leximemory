package com.leximemory.backend.controllers.dto;

import com.leximemory.backend.models.enums.SubjectsInterests;
import java.util.List;

/**
 * The type User creation dto.
 */
public record UserCreationDto(
    String name,
    String email,
    String password,
    List<SubjectsInterests> subjectsInterests
) {

}
