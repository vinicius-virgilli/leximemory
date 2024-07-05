package com.leximemory.backend.controllers;

import com.leximemory.backend.controllers.dto.questiondto.QuestionDto;
import com.leximemory.backend.services.QuestionService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

/**
 * The type Question controller.
 */
@RestController
@RequestMapping("/questions")
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
   * Gets all questions.
   *
   * @return the all questions
   */
  @GetMapping
  @ResponseStatus(HttpStatus.OK)
  public List<QuestionDto> getAllQuestions() {
    return questionService.getAllQuestions().stream().map(QuestionDto::fromEntity).toList();
  }
}
