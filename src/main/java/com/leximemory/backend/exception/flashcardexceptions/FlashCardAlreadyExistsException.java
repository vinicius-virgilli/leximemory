package com.leximemory.backend.exception.flashcardexceptions;

import com.leximemory.backend.exception.AlreadyExistsException;

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

  /**
   * The type User already exists.
   */
  public static class UserWordAlreadyExistsException extends AlreadyExistsException {

    /**
     * Instantiates a new User already exists.
     */
    public UserWordAlreadyExistsException() {
      super("UserWord already exists!");
    }
  }
}
