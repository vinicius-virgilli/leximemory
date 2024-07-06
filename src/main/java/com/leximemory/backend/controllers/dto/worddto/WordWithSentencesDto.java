package com.leximemory.backend.controllers.dto.worddto;

import com.leximemory.backend.controllers.dto.sentencedto.SentenceResponseDto;
import com.leximemory.backend.models.entities.Word;
import com.leximemory.backend.models.enums.WordType;
import java.util.List;

/**
 * The type Complete word dto.
 */
public record WordWithSentencesDto(
    Integer id,
    WordType wordType,
    String word,
    String meaning,
    Integer wordRank,
    Integer repetitions,
    List<SentenceResponseDto> sentence
) {

  /**
   * From entity complete word dto.
   *
   * @param word the word
   * @return the complete word dto
   */
  public static WordWithSentencesDto fromEntity(Word word) {
    return new WordWithSentencesDto(
        word.getId(),
        word.getType(),
        word.getWord(),
        word.getMeaning(),
        word.getWordRank(),
        word.getRepetitions(),
        word.getExempleSentences()
            .stream()
            .map(SentenceResponseDto::fromEntity)
            .toList()
    );
  }
}
