package com.leximemory.backend.models.entities;

import com.leximemory.backend.models.enums.ReviewType;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.time.LocalDate;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * The type Review.
 */
@Entity
@Table(name = "reviews")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Review {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;
  private String title;
  @ElementCollection
  private List<ReviewType> reviewTypes;
  private LocalDate registrationDate;
  private LocalDate proposedDate;
  private LocalDate executionDate;
  private Float performance;

  @ManyToOne
  @JoinColumn(name = "user_text_id")
  private UserText userText;

  @ManyToMany
  @JoinTable(
      name = "review_question",
      joinColumns = @JoinColumn(name = "review_id"),
      inverseJoinColumns = @JoinColumn(name = "question_id")
  )
  private List<Question> questions;

  @ManyToMany
  @JoinTable(
      name = "review_flashcard",
      joinColumns = @JoinColumn(name = "review_id"),
      inverseJoinColumns = @JoinColumn(name = "flashcard_id")
  )
  private List<FlashCard> flashcards;

  @ManyToOne
  @JoinColumn(name = "user_id")
  private User user;
}
