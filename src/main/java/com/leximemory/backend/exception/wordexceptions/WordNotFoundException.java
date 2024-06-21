package com.leximemory.backend.exception.wordexceptions;

import com.leximemory.backend.exception.NotFoundException;

/**
 * The type User not found exception.
 */
public class WordNotFoundException extends NotFoundException {

  /**
   * Instantiates a new User not found exception.
   */
  public WordNotFoundException() {
    super("Word not found!");
  }
}
