package com.leximemory.backend.services.exception.userexceptions;

import com.leximemory.backend.services.exception.NotFoundException;

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
