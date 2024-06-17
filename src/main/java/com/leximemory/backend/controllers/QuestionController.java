package com.leximemory.backend.controllers;

import com.leximemory.backend.controllers.dto.QuestionCreationDto;
import com.leximemory.backend.models.entities.Question;
import com.leximemory.backend.services.QuestionService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

/**
 * The type Question controller.
 */
@RestController
@RequestMapping("/words/{wordId}")
public class QuestionController {

  private final QuestionService questionService;

  /**
   * Instantiates a new Question controller.
   *
   * @param questionService the question service
   */
  @Autowired

  public QuestionController(QuestionService questionService) {
    this.questionService = questionService;
  }

  /**
   * Create question string.
   *
   * @param wordId              the word id
   * @param questionCreationDto the question creation dto
   * @return the string
   */
  @PostMapping("/questions")
  @ResponseStatus(HttpStatus.CREATED)
  public String createQuestion(
      @PathVariable Integer wordId,
      @RequestBody QuestionCreationDto questionCreationDto
  ) {
    return questionService.addQuestionToWord(wordId, questionCreationDto.toEntity());
  }

  /**
   * Gets all word questions.
   *
   * @param wordId the word id
   * @return the all word questions
   */
  @GetMapping("/questions")
  @ResponseStatus(HttpStatus.OK)
  public List<Question> getAllWordQuestions(@PathVariable Integer wordId) {
    return questionService.getAllWordQuestions(wordId);
  }
}
