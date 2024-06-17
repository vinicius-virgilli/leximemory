package com.leximemory.backend.exception;

/**
 * The type User not found exception.
 */
public class WordNotFoundException extends NotFoundException {

  /**
   * Instantiates a new User not found exception.
   */
  public WordNotFoundException() {
    super("Word not found!");
  }
}
