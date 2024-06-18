package com.leximemory.backend.exception;

/**
 * The type User already exists.
 */
public class UserWordAlreadyExistsException extends AlreadyExistsException {

  /**
   * Instantiates a new User already exists.
   */
  public UserWordAlreadyExistsException() {
    super("UserWord already exists!");
  }
}
