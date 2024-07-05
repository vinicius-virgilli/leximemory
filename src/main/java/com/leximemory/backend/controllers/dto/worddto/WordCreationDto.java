package com.leximemory.backend.controllers.dto.worddto;

import java.util.List;

/**
 * The type Word creation dto.
 */
public record WordCreationDto(
    String word,
    Integer rank,
    Integer repetitions,
    List<String> sentences,
    List<String> translations
) {

}
