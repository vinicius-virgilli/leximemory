package com.leximemory.backend.models.entities;

import com.leximemory.backend.models.entities.EntitiesId.QuestionId;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;
import jakarta.persistence.Table;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * The type Question.
 */
@Entity
@Table(name = "questions")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Question {

  @EmbeddedId
  private QuestionId id;
  @ManyToOne
  @MapsId("wordId")
  @JoinColumn(name = "word_id")
  private Word word;
  private String type;
  private String statement;
  private List<String> wrongAlternatives;
  private String correctAlternative;
  private Integer difficultyLevel;
}
