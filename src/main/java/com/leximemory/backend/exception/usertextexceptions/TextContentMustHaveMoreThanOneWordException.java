package com.leximemory.backend.exception.usertextexceptions;


import com.leximemory.backend.exception.MethodArgumentNotValidException;

/**
 * The type Text content must have more than one word exception.
 */
public class TextContentMustHaveMoreThanOneWordException extends MethodArgumentNotValidException {


  /**
   * Instantiates a new Text content must have more than one word exception.
   */
  public TextContentMustHaveMoreThanOneWordException() {
    super("text content must have more than one word!");
  }
}
