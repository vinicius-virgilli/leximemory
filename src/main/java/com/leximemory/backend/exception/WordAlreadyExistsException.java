package com.leximemory.backend.exception;

/**
 * The type Word already exists exeption.
 */
public class WordAlreadyExistsException extends AlreadyExistsException {

  /**
   * Instantiates a new Word already exists exeption.
   */
  public WordAlreadyExistsException() {
    super("Word already exists!");
  }
}
