package com.leximemory.backend.controllers.dto;

import com.leximemory.backend.models.entities.UserWord;
import com.leximemory.backend.models.entities.id.UserWordId;
import com.leximemory.backend.models.enums.DifficultyLevel;
import com.leximemory.backend.models.enums.Temperature;
import java.time.LocalDateTime;

/**
 * The type User word dto.
 */
public record UserWordDto(
    UserWordId id,
    LocalDateTime registrationDate,
    LocalDateTime lastReviewDate,
    Integer reviewsCount,
    DifficultyLevel difficultyLevel,
    Temperature temperature
) {

  /**
   * Converts this DTO to an entity.
   *
   * @param id the id
   * @return the user word entity
   */
  public UserWord toEntity(UserWordId id) {
    UserWord userWord = new UserWord();
    userWord.setId(id);
    userWord.setRegistrationDate(LocalDateTime.now());
    userWord.setLastRevisionDate(LocalDateTime.now());
    userWord.setReviewsCount(0);
    userWord.setDifficultyLevel(this.difficultyLevel);
    return userWord;
  }

  /**
   * Converts an entity to this DTO.
   *
   * @param userWord the user word entity
   * @return the user word DTO
   */
  public static UserWordDto fromEntity(UserWord userWord) {
    return new UserWordDto(
        userWord.getId(),
        userWord.getRegistrationDate(),
        userWord.getLastRevisionDate(),
        userWord.getReviewsCount(),
        userWord.getDifficultyLevel(),
        userWord.getTemperature()
    );
  }
}
