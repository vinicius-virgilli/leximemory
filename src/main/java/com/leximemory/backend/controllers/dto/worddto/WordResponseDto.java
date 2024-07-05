package com.leximemory.backend.controllers.dto.worddto;

import com.leximemory.backend.models.entities.Word;
import com.leximemory.backend.models.enums.WordType;
import java.util.List;

/**
 * The type Word response dto.
 */
public record WordResponseDto(
    Integer id,
    WordType type,
    String word,
    Integer wordRank,
    Integer repetitions
) {

  /**
   * From entity word response dto.
   *
   * @param word the word
   * @return the word response dto
   */
  public static WordResponseDto fromEntity(Word word) {
    return new WordResponseDto(
        word.getId(),
        word.getType(),
        word.getWord(),
        word.getWordRank(),
        word.getRepetitions()
    );
  }
}
