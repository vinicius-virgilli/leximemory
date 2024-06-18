package com.leximemory.backend.exception;

/**
 * The type User already exists.
 */
public class FlashCardAlreadyExistsException extends AlreadyExistsException {

  /**
   * Instantiates a new User already exists.
   */
  public FlashCardAlreadyExistsException() {
    super("UserWord already has flashcard!");
  }
}
