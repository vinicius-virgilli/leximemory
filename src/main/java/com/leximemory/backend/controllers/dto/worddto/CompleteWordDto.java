package com.leximemory.backend.controllers.dto.worddto;

import com.leximemory.backend.controllers.dto.sentencedto.SentenceDto;
import com.leximemory.backend.controllers.dto.sentencedto.SentenceResponseDto;
import com.leximemory.backend.models.entities.Word;
import com.leximemory.backend.models.enums.WordType;
import java.util.List;

/**
 * The type Complete word dto.
 */
public record CompleteWordDto(
    Integer id,
    WordType wordType,
    String word,
    String meaning,
    Integer wordRank,
    Integer repetitions,
    List<SentenceResponseDto> sentence,
    byte[] audio
) {

  /**
   * From entity complete word dto.
   *
   * @param word the word
   * @return the complete word dto
   */
  public static CompleteWordDto fromEntity(Word word) {
    return new CompleteWordDto(
        word.getId(),
        word.getType(),
        word.getWord(),
        word.getMeaning(),
        word.getWordRank(),
        word.getRepetitions(),
        word.getExempleSentences()
            .stream()
            .map(SentenceResponseDto::fromEntity)
            .toList(),
        word.getAudio().getAudio()
    );
  }
}
