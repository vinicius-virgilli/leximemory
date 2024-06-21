package com.leximemory.backend.exception.userexceptions;

import com.leximemory.backend.exception.NotFoundException;

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
