package com.leximemory.backend.controllers.dto.sentencedto;

import java.util.List;

/**
 * The type Sentences creation dto.
 */
public record SentencesCreationDto(
    List<SentenceCreationDto> sentences
) {

}
