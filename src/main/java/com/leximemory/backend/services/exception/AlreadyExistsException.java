package com.leximemory.backend.services.exception;

/**
 * The type Custom error.
 */
public class AlreadyExistsException extends RuntimeException {

  /**
   * Instantiates a new Custom error.
   *
   * @param message the message
   */
  public AlreadyExistsException(String message) {
    super(message);
  }
}
