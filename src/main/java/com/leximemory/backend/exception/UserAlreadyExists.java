package com.leximemory.backend.exception;

/**
 * The type User already exists.
 */
public class UserAlreadyExists extends AlreadyExistsException {

  /**
   * Instantiates a new User already exists.
   */
  public UserAlreadyExists() {
    super("User already exists!");
  }
}
