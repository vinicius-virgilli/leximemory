package com.leximemory.backend.models.entities;

import com.leximemory.backend.models.enums.DifficultyLevel;
import com.leximemory.backend.models.enums.SubjectsInterests;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinColumns;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * The type User text.
 */
@Entity
@Table(name = "user_text")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserText {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;
  private String title;
  private SubjectsInterests subject;
  private Integer wordsTotal;
  private Integer newUserWordsCount;
  private DifficultyLevel difficultyLevel;

  @ManyToOne
  @JoinColumn(name = "user_id")
  private User user;

  @OneToMany(mappedBy = "userText")
  private List<Sentence> sentences;

  @OneToMany(mappedBy = "userText")
  private List<Question> questions;

  @OneToMany(mappedBy = "userText")
  private List<Review> reviews;

}
