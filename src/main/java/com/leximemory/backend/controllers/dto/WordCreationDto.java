package com.leximemory.backend.controllers.dto;

import com.leximemory.backend.models.entities.Word;
import com.leximemory.backend.models.enums.WordType;
import java.util.List;

/**
 * The type Word creation dto.
 */
public record WordCreationDto(
    WordType type,
    String word,
    String meaning,
    List<String> exampleSentence
) {

  /**
   * To entity word.
   *
   * @return the word
   */
  public Word toEntity() {
    Word word = new Word();
    word.setType(this.type());
    word.setWord(this.word());
    word.setMeaning(this.meaning());
    word.setExampleSentence(this.exampleSentence());
    return word;
  }
}
