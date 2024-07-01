package com.leximemory.backend.controllers.dto;

import com.leximemory.backend.models.entities.UserText;
import com.leximemory.backend.models.enums.DifficultyLevel;
import com.leximemory.backend.models.enums.SubjectsInterests;
import java.util.List;

/**
 * The type User text response.
 */
public record UserTextResponseDto(
    Integer id,
    Integer wordsTotal,
    Integer newUserWordsCount,
    DifficultyLevel difficultyLevel,
    SubjectsInterests subject,
    String title,
    List<SentenceDto> sentenceDtos
) {

  /**
   * From entity user text response dto.
   *
   * @param userText the user text
   * @return the user text response dto
   */
  public static UserTextResponseDto fromEntity(UserText userText) {
    return new UserTextResponseDto(
        userText.getId(),
        userText.getWordsTotal(),
        userText.getNewUserWordsCount(),
        userText.getDifficultyLevel(),
        userText.getSubject(),
        userText.getTitle(),
        userText.getSentences().stream().map(SentenceDto::fromEntity).toList()
    );
  }

  /**
   * From entity user text response dto.
   *
   * @param userText the user text
   * @param content  the content
   * @return the user text response dto
   */
  public static UserTextResponseDto fromEntity(UserText userText, List<UserWordTextDto> content) {
    return new UserTextResponseDto(
        userText.getId(),
        userText.getWordsTotal(),
        userText.getNewUserWordsCount(),
        userText.getDifficultyLevel(),
        userText.getSubject(),
        userText.getTitle(),
        userText.getSentences().stream().map(
            SentenceDto::fromEntity
        ).toList()
    );
  }
}
