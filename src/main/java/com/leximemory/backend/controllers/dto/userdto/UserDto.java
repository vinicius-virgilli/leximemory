package com.leximemory.backend.controllers.dto.userdto;

import com.leximemory.backend.models.entities.User;
import com.leximemory.backend.models.enums.SubjectsInterests;
import java.time.LocalDateTime;
import java.util.List;

/**
 * The type User creation dto.
 */
public record UserDto(
    Integer id,
    String name,
    String email,
    String password,
    List<SubjectsInterests> subjectsInterests,
    LocalDateTime registrationDate
) {

  /**
   * To entity user.
   *
   * @return the user
   */
  public User toEntity() {
    User user = new User();
    user.setName(this.name());
    user.setEmail(this.email());
    user.setPassword(this.password());
    user.setSubjectsInterests(this.subjectsInterests());
    return user;
  }

  /**
   * From entity user dto.
   *
   * @param user the user
   * @return the user dto
   */
  public static UserDto fromEntity(User user) {
    return new UserDto(
        user.getId(),
        user.getName(),
        user.getEmail(),
        user.getPassword(),
        user.getSubjectsInterests(),
        user.getRegistrationDate()
    );
  }
}
