package com.leximemory.backend.services.exceptions;

public class UserNotFoundException extends Exception{

  public UserNotFoundException() {
    super("User not found!");
  }
}
