package com.leximemory.backend.controllers.dto.worddto;


/**
 * The type Word creation dto.
 */
public record WordCreationDto(
    String word,
    Integer rank,
    Integer repetitions
) {

}
