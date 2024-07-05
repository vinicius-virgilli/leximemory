package com.leximemory.backend.controllers.dto.worddto;

import com.leximemory.backend.models.entities.Sentence;
import com.leximemory.backend.models.entities.UserWord;
import com.leximemory.backend.models.entities.id.UserWordId;
import java.util.List;

/**
 * The type Word sentence dto.
 */
public record WordSentenceDto(
    Integer id,
    String textSentence,
    String translation,
    List<UserWordId> userWordIds
) {

  /**
   * From entity word sentence dto.
   *
   * @param sentence the sentence
   * @return the word sentence dto
   */
  public static WordSentenceDto fromEntity(Sentence sentence) {
    return new WordSentenceDto(
        sentence.getId(),
        sentence.getTextSentence(),
        sentence.getTranslation(),
        sentence.getSentence().stream().map(UserWord::getId).toList()
    );
  }
}
