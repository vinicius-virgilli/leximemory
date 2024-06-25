package com.leximemory.backend.models.entities;

import com.leximemory.backend.models.enums.DifficultyLevel;
import com.leximemory.backend.models.enums.QuestionType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
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

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;
  private QuestionType type;
  private String statement;
  private List<String> wrongAlternatives;
  private String correctAlternative;
  private DifficultyLevel difficultyLevel;

  @ManyToOne
  @JoinColumn(name = "word_id")
  private Word word;

  @ManyToOne
  @JoinColumn(name = "user_text_id")
  private UserText userText;

  @ManyToMany(mappedBy = "questions")
  private List<Review> reviews;
}
