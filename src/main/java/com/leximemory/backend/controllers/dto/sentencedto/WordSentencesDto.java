package com.leximemory.backend.controllers.dto.sentencedto;

import com.leximemory.backend.models.entities.Word;
import java.util.List;

/**
 * The type Word sentences dto.
 */
public record WordSentencesDto(
    List<WordSentenceDto> sentences
) {

  /**
   * From entity word sentences dto.
   *
   * @param word the word
   * @return the word sentences dto
   */
  public static WordSentencesDto fromEntity(Word word) {
    return new WordSentencesDto(word.getExempleSentences().stream()
        .map(WordSentenceDto::fromEntity).toList());
  }
}
