package com.leximemory.backend.exception.usertextexceptions;

import com.leximemory.backend.exception.NotFoundException;


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
