package com.leximemory.backend.controllers.dto;

import com.leximemory.backend.models.entities.UserText;
import com.leximemory.backend.models.enums.DifficultyLevel;
import com.leximemory.backend.models.enums.SubjectsInterests;


/**
 * The type User text dto.
 */
public record UserTextDto(
    Integer id,
    Integer userId,
    DifficultyLevel difficultyLevel,
    Integer wordsTotal,
    Integer newUserWordsCount,
    SubjectsInterests subject,
    String title,
    String content
) {
  /**
   * From entity user text dto.
   *
   * @param userText the user text
   * @return the user text dto
   */
  public static UserTextDto fromEntity(UserText userText) {
    return new UserTextDto(
        userText.getId(),
        userText.getUser().getId(),
        userText.getDifficultyLevel(),
        userText.getWordsTotal(),
        userText.getNewUserWordsCount(),
        userText.getSubject(),
        userText.getTitle(),
        "Content saved in List<UserWord> format!"
    );
  }
}
