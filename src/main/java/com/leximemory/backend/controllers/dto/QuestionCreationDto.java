package com.leximemory.backend.controllers.dto;

import com.leximemory.backend.models.entities.Question;
import com.leximemory.backend.models.enums.QuestionDifficultyLevel;
import com.leximemory.backend.models.enums.QuestionType;
import java.util.List;

/**
 * The type Question creation dto.
 */
public record QuestionCreationDto(
    QuestionType type,
    String statement,
    List<String> wrongAlternatives,
    String correctAlternative,

    QuestionDifficultyLevel difficultyLevel
) {

  /**
   * To entity question.
   *
   * @return the question
   */
  public Question toEntity() {
    Question question = new Question();
    question.setType(this.type());
    question.setStatement(this.statement());
    question.setWrongAlternatives(this.wrongAlternatives());
    question.setCorrectAlternative(this.correctAlternative());
    question.setDifficultyLevel(this.difficultyLevel());
    return question;
  }
}
