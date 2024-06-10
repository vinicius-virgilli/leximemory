package com.leximemory.backend.util;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/**
 * The type Encoder.
 */
public class Encoder {
  /**
   * Encode password string.
   *
   * @param password the password
   * @return the string
   */
  public static String encodePassword(String password) {
    BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
    return encoder.encode(password);
  }
}
