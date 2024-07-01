package com.leximemory.backend.controllers.dto;

import com.leximemory.backend.models.entities.Sentence;
import java.util.List;

/**
 * The type Sentence dto.
 */
public record SentenceDto(
    Integer id,
    Integer userTextId,
    List<UserWordTextDto> sentence
) {

  /**
   * To entity sentence.
   *
   * @return the sentence
   */
  public Sentence toEntity() {
    return new Sentence();
  }

  /**
   * From entity sentence dto.
   *
   * @param sentence the sentence
   * @return the sentence dto
   */
  public static SentenceDto fromEntity(Sentence sentence) {
    return new SentenceDto(
        sentence.getId(),
        sentence.getUserText().getId(),
        sentence.getSentence().stream().map(UserWordTextDto::fromEntity).toList()
    );
  }
}
