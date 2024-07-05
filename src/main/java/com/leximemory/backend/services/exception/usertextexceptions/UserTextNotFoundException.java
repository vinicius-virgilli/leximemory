package com.leximemory.backend.services.exception.usertextexceptions;

import com.leximemory.backend.services.exception.NotFoundException;


/**
 * The type User text not found exception.
 */
public class UserTextNotFoundException extends NotFoundException {

  /**
   * Instantiates a new User text not found exception.
   */
  public UserTextNotFoundException() {
    super("UserText not found!");
  }
}
