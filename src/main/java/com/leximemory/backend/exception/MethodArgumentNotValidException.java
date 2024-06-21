package com.leximemory.backend.exception;


/**
 * The type Method argument not valid exception.
 */
public class MethodArgumentNotValidException extends RuntimeException {


  /**
   * Instantiates a new Method argument not valid exception.
   *
   * @param message the message
   */
  public MethodArgumentNotValidException(String message) {
    super(message);
  }
}
