package com.leximemory.backend.controllers.dto.usertextdto;

import com.leximemory.backend.controllers.dto.worddto.WordDto;
import com.leximemory.backend.models.entities.UserWord;
import com.leximemory.backend.models.enums.DifficultyLevel;
import com.leximemory.backend.models.enums.Temperature;

/**
 * The type User word text dto.
 */
public record UserWordTextDto(
    WordDto word,
    DifficultyLevel difficultyLevel,
    Temperature temperature
) {

  /**
   * From entity user word text dto.
   *
   * @param userWord the user word
   * @return the user word text dto
   */
  public static UserWordTextDto fromEntity(UserWord userWord) {
    return new UserWordTextDto(
        WordDto.fromEntity(userWord.getWord()),
        userWord.getDifficultyLevel(),
        userWord.getTemperature()
    );
  }
}
