package com.leximemory.backend.exception;

/**
 * The type User not found exception.
 */
public class UserNotFoundException extends NotFoundException {

  /**
   * Instantiates a new User not found exception.
   */
  public UserNotFoundException() {
    super("User not found!");
  }
}
