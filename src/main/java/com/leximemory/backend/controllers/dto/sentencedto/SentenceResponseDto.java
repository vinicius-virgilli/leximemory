package com.leximemory.backend.controllers.dto.sentencedto;

import com.leximemory.backend.models.entities.Sentence;
import com.leximemory.backend.models.entities.UserWord;
import com.leximemory.backend.models.entities.id.UserWordId;
import java.util.List;

/**
 * The type Sentence response dto.
 */
public record SentenceResponseDto(
    Integer id,
    String textSentence,
    String translation,
    List<UserWordId> sentence,
    byte[] audio
) {

  /**
   * From entity sentence response dto.
   *
   * @param sentence the sentence
   * @return the sentence response dto
   */
  public static SentenceResponseDto fromEntity(Sentence sentence) {
    return new SentenceResponseDto(
        sentence.getId(),
        sentence.getTextSentence(),
        sentence.getTranslation(),
        sentence.getSentence().stream().map(
            UserWord::getId
        ).toList(),
        sentence.getAudio().getAudio()
    );
  }
}
