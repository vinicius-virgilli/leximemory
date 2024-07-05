package com.leximemory.backend.services.exception.sentenceexception;

import com.leximemory.backend.services.exception.NotFoundException;


/**
 * The type Sentence not found exception.
 */
public class SentenceNotFoundException extends NotFoundException {

  /**
   * Instantiates a new Sentence not found exception.
   */
  public SentenceNotFoundException() {
    super("Sentence not found!");
  }
}
