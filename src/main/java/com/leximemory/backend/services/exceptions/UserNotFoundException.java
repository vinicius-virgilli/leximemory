package com.leximemory.backend.services.exceptions;

/**
 * The type User not found exception.
 */
public class UserNotFoundException extends Exception {

  /**
   * Instantiates a new User not found exception.
   */
  public UserNotFoundException() {
    super("User not found!");
  }
}
