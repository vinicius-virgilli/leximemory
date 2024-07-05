package com.leximemory.backend.services.exception.wordexceptions;

import com.leximemory.backend.services.exception.IoException;

/**
 * The type Word io exception.
 */
public class WordIoException extends IoException {

  /**
   * Instantiates a new Word io exception.
   */
  public WordIoException() {
    super("An I/O error occurred while processing the word operation");
  }
}
