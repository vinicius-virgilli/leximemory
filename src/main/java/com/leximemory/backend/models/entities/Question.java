package com.leximemory.backend.models.entities;

import com.leximemory.backend.models.enums.QuestionDifficultyLevel;
import com.leximemory.backend.models.enums.QuestionType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;
import jakarta.persistence.Table;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * The type Question. Uma word tem várias questions, e uma question tem uma word.
 */
@Entity
@Table(name = "questions")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Question {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;

  private Integer wordId;
  private QuestionType type;
  private String statement;
  private List<String> wrongAlternatives;
  private String correctAlternative;
  private QuestionDifficultyLevel difficultyLevel;
}
