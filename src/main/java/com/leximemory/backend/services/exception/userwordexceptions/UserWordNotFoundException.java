package com.leximemory.backend.services.exception.userwordexceptions;

import com.leximemory.backend.services.exception.NotFoundException;

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
