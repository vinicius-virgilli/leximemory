package com.leximemory.backend.controllers.dto;

import com.leximemory.backend.models.enums.SubjectsInterests;
import java.util.List;

/**
 * The type User dto.
 */
public record UserResponseDto(
    Integer id,
    String name,
    String email,
    List<SubjectsInterests> subjectsInterests) {

}
