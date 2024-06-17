package com.leximemory.backend.exception;

/**
 * The type User already exists.
 */
public class UserWordAlreadyExists extends AlreadyExistsException {

  /**
   * Instantiates a new User already exists.
   */
  public UserWordAlreadyExists() {
    super("User word already exists!");
  }
}
