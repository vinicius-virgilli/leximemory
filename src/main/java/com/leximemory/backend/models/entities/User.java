package com.leximemory.backend.models.entities;

import com.leximemory.backend.models.enums.SubjectsInterests;
import com.leximemory.backend.models.enums.UserType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
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

  private UserType type;
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

  @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
  private List<UserWord> userWords;

  @OneToMany(mappedBy = "user")
  private List<UserText> userTexts;

  @OneToMany(mappedBy = "user")
  private List<FlashCard> flashCards;

  @OneToMany(mappedBy = "user")
  private List<Review> reviews;

}
