package com.leximemory.backend.models.entities;

import com.leximemory.backend.models.entities.id.UserWordId;
import com.leximemory.backend.models.enums.DifficultyLevel;
import com.leximemory.backend.models.enums.Temperature;
import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * The type User word.
 */
@Entity
@Table(name = "users_words")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserWord {

  @EmbeddedId
  private UserWordId id;
  private LocalDateTime registrationDate;
  private LocalDateTime lastRevisionDate;
  private Integer reviewsCount;
  private DifficultyLevel difficultyLevel;
  private Temperature temperature;

  @ManyToOne
  @MapsId("userId")
  @JoinColumn(name = "user_id")
  private User user;

  @ManyToOne
  @MapsId("wordId")
  @JoinColumn(name = "word_id")
  private Word word;

  @OneToOne(mappedBy = "userWord")
  private FlashCard flashCard;

  @ManyToMany(mappedBy = "sentence")
  List<Sentence> sentences;
}
