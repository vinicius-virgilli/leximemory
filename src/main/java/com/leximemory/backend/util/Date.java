package com.leximemory.backend.util;

import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * The type Date.
 */
public class Date {

  /**
   * Current LocalDate.
   *
   * @return the timestamp
   */
  public static LocalDate currentDate() {
    return LocalDateTime.now().toLocalDate();
  }
}
