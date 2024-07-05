package com.leximemory.backend.controllers.dto.worddto;

import com.leximemory.backend.controllers.dto.sentencedto.SentenceDto;
import com.leximemory.backend.models.entities.Word;
import com.leximemory.backend.models.enums.WordType;
import java.util.List;

/**
 * The type Word creation dto.
 */
public record WordDto(
    Integer id,
    WordType type,
    String word,
    String meaning,
    Integer wordRank,
    Integer repetitions
) {

  /**
   * To entity word.
   *
   * @return the word
   */
  public Word toEntity() {
    Word word = new Word();
    word.setWord(this.word());
    return word;
  }

  /**
   * From entity word dto.
   *
   * @param word the word
   * @return the word dto
   */
  public static WordDto fromEntity(Word word) {
    return new WordDto(
        word.getId(),
        word.getType(),
        word.getWord(),
        word.getMeaning(),
        word.getWordRank(),
        word.getRepetitions()
    );
  }
}
