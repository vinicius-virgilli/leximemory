package com.leximemory.backend.controllers.dto;

import com.leximemory.backend.models.entities.User;
import java.time.LocalDate;

/**
 * The type User creation dto.
 */
public record UserCreationDto(
    String name,
    String email,
    String password,
    LocalDate registrationDate
) {

  /**
   * To user user.
   *
   * @return the user
   */
  public User toUser() {
    User user = new User();
    user.setName(this.name());
    user.setEmail(this.email());
    user.setPassword(this.password());
    user.setRegistrationDate(this.registrationDate());
    return user;
  }
}
