package com.leximemory.backend.services.exception.userwordexceptions;

import com.leximemory.backend.services.exception.AlreadyExistsException;

/**
 * The type Word already exists exeption.
 */
public class WordAlreadyExistsException extends AlreadyExistsException {

  /**
   * Instantiates a new Word already exists exeption.
   */
  public WordAlreadyExistsException() {
    super("Word already exists!");
  }
}
