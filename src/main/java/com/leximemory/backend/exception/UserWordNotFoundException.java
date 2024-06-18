package com.leximemory.backend.exception;

/**
 * The type User not found exception.
 */
public class UserWordNotFoundException extends NotFoundException {

  /**
   * Instantiates a new User not found exception.
   */
  public UserWordNotFoundException() {
    super("UserWord not found!");
  }
}
