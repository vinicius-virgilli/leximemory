package com.leximemory.backend.services.exception.questionexceptions;

import com.leximemory.backend.services.exception.NotFoundException;

/**
 * The type User not found exception.
 */
public class QuestionNotFoundException extends NotFoundException {


  /**
   * Instantiates a new Question not found exception.
   */
  public QuestionNotFoundException() {
    super("Question not found!");
  }
}
