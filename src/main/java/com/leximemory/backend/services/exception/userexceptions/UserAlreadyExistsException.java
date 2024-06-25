package com.leximemory.backend.services.exception.userexceptions;

import com.leximemory.backend.services.exception.AlreadyExistsException;

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
