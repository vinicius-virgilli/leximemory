package com.leximemory.backend.controllers.dto;

import com.leximemory.backend.models.entities.Question;
import com.leximemory.backend.models.enums.DifficultyLevel;
import com.leximemory.backend.models.enums.QuestionType;
import java.util.List;

/**
 * The type Question creation dto.
 */
public record QuestionDto(
    Integer id,
    QuestionType type,
    String statement,
    List<String> wrongAlternatives,
    String correctAlternative,
    DifficultyLevel difficultyLevel
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

  /**
   * From entity question dto.
   *
   * @param question the question
   * @return the question dto
   */
  public static QuestionDto fromEntity(Question question) {
    return new QuestionDto(
        question.getId(),
        question.getType(),
        question.getStatement(),
        question.getWrongAlternatives(),
        question.getCorrectAlternative(),
        question.getDifficultyLevel()
    );
  }
}
