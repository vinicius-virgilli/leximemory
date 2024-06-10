package com.leximemory.backend.util;

import java.sql.Timestamp;
import java.time.LocalDateTime;

/**
 * The type Date.
 */
public class Date {

  /**
   * Current instant timestamp.
   *
   * @return the timestamp
   */
  public static Timestamp currentInstant() {
    return Timestamp.valueOf(LocalDateTime.now());
  }
}
