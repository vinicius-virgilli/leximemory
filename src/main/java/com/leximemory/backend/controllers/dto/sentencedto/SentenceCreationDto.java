package com.leximemory.backend.controllers.dto.sentencedto;

import java.util.List;

/**
 * The type Sentence creation dto.
 */
public record SentenceCreationDto(
    List<String> sentences,
    List<String> translations,
    Integer tatoebaAudioId
) {

}
