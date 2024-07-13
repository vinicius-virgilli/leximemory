package com.leximemory.backend.controllers.dto.sentencedto;

import java.util.List;

/**
 * The type Sentence creation dto.
 */
public record SentenceCreationDto(
    String sentence,
    Integer tatoebaAudioId,
    String translation
) {

}
