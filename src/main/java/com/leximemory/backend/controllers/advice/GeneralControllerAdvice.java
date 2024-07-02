package com.leximemory.backend.controllers.advice;

import com.leximemory.backend.services.exception.AlreadyExistsException;
import com.leximemory.backend.services.exception.MethodArgumentNotValidException;
import com.leximemory.backend.services.exception.NotFoundException;
import com.leximemory.backend.services.exception.wordexceptions.WordIoException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

/**
 * The type General controller advice.
 */
@ControllerAdvice
public class GeneralControllerAdvice {

  /**
   * Handle not found exception response entity.
   *
   * @param e the e
   * @return the response entity
   */
  @ExceptionHandler(NotFoundException.class)
  public ResponseEntity<String> handleNotFoundException(NotFoundException e) {
    return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
  }

  /**
   * Handle user already exists exception response entity.
   *
   * @param e the e
   * @return the response entity
   */
  @ExceptionHandler(AlreadyExistsException.class)
  public ResponseEntity<String> handleUserAlreadyExistsException(AlreadyExistsException e) {
    return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
  }

  /**
   * Handle method argument not valid exception response entity.
   *
   * @param e the e
   * @return the response entity
   */
  @ExceptionHandler(MethodArgumentNotValidException.class)
  public ResponseEntity<String> handleMethodArgumentNotValidException(
      MethodArgumentNotValidException e
  ) {
    return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
  }

  /**
   * Handle word io exception response entity.
   *
   * @param e the e
   * @return the response entity
   */
  @ExceptionHandler(WordIoException.class)
  public ResponseEntity<String> handleWordIoException(WordIoException e) {
    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
  }
}
