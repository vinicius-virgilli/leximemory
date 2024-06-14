package com.leximemory.backend.models.entities;

import com.leximemory.backend.models.entities.entitiesId.UserWordId;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * The type User word.
 */
@Entity
@Table(name = "users_words")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserWord {

  @EmbeddedId
  private UserWordId id;

  @ManyToOne
  @MapsId("userId")
  @JoinColumn(name = "user_id")
  private User user;

  @ManyToOne
  @MapsId("wordId")
  @JoinColumn(name = "word_id")
  private Word word;

  private LocalDateTime registrationDate;
  private LocalDateTime lastRevisionDate;
  private Integer reviewsCount;
  private Integer difficultyLevel;
  private String temperature;
}
