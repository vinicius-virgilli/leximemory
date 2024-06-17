package com.leximemory.backend.models.entities;

import com.leximemory.backend.controllers.dto.UserResponseDto;
import com.leximemory.backend.models.enums.SubjectsInterests;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

/**
 * The type User.
 */
@Entity
@Table(name = "users")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)

  private Integer id;
  @NonNull
  private String name;
  @NonNull
  private String email;
  @NonNull
  private String password;
  @NonNull
  private List<SubjectsInterests> subjectsInterests;
  @NonNull
  private LocalDateTime registrationDate;

  /**
   * To dto user dto.
   *
   * @return the user dto
   */
  public UserResponseDto toResponseDto() {
    return new UserResponseDto(this.id, this.name, this.email, this.subjectsInterests);
  }
}
