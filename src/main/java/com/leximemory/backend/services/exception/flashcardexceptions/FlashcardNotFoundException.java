package com.leximemory.backend.services.exception.flashcardexceptions;

import com.leximemory.backend.services.exception.NotFoundException;


/**
 * The type Flashcard not found exception.
 */
public class FlashcardNotFoundException extends NotFoundException {


  /**
   * Instantiates a new Flashcard not found exception.
   */
  public FlashcardNotFoundException() {
    super("Flashcard not found!");
  }
}
