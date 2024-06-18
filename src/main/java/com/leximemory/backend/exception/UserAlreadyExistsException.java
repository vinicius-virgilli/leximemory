package com.leximemory.backend.exception;

/**
 * The type User already exists.
 */
public class UserAlreadyExistsException extends AlreadyExistsException {

  /**
   * Instantiates a new User already exists.
   */
  public UserAlreadyExistsException() {
    super("User already exists!");
  }
}
