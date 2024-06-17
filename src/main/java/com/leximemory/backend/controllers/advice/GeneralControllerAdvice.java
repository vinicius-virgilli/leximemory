package com.leximemory.backend.controllers.advice;

import com.leximemory.backend.exception.AlreadyExistsException;
import com.leximemory.backend.exception.NotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

/**
 * The type General controller advice.
 */
@ControllerAdvice
public class GeneralControllerAdvice {
  @ExceptionHandler(NotFoundException.class)
  public ResponseEntity<String> handleNotFoundException(NotFoundException e) {
    return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
  }

  @ExceptionHandler(AlreadyExistsException.class)
  public ResponseEntity<String> handleUserAlreadyExistsException(AlreadyExistsException e) {
    return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
  }
}
