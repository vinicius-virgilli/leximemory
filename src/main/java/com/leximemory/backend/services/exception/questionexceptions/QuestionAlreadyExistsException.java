package com.leximemory.backend.services.exception.questionexceptions;

import com.leximemory.backend.services.exception.AlreadyExistsException;

/**
 * The type User already exists.
 */
public class QuestionAlreadyExistsException extends AlreadyExistsException {

  /**
   * Instantiates a new User already exists.
   */
  public QuestionAlreadyExistsException() {
    super("Question already exists!");
  }
}
