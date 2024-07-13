package com.leximemory.backend.controllers.dto.sentencedto;

import com.leximemory.backend.models.entities.Sentence;
import java.util.ArrayList;

/**
 * The type Sentence dto.
 */
public record WordSentenceDto(
    Integer id,
    Integer wordId,
    Integer tatoebaAudioId,
    String textSentence,
    String translation
) {

  /**
   * To entity sentence.
   *
   * @return the sentence
   */
  public Sentence toEntity() {
    return new Sentence(
        id,
        textSentence,
        translation,
        tatoebaAudioId,
        new ArrayList<>(),
        null,
        null,
        null
    );
  }

  /**
   * From entity sentence dto.
   *
   * @param sentence the sentence
   * @return the sentence dto
   */
  public static WordSentenceDto fromEntity(Sentence sentence) {
    return new WordSentenceDto(
        sentence.getId(),
        sentence.getWord().getId(),
        sentence.getTatoebaAudioId(),
        sentence.getTextSentence(),
        sentence.getTranslation()
    );
  }
}
