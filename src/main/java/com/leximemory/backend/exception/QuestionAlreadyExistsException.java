package com.leximemory.backend.exception;

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
