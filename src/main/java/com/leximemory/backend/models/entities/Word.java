package com.leximemory.backend.models.entities;

import com.leximemory.backend.models.enums.WordType;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * The type Word.
 */
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@Table(name = "words")
public class Word {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;

  private WordType type;
  private String word;
  private String meaning;
  @ElementCollection
  private List<String> exampleSentence;

  @OneToMany(mappedBy = "word", fetch = FetchType.LAZY)
  private List<Question> questions;

  @OneToMany(mappedBy = "word", fetch = FetchType.LAZY)
  private List<UserWord> userWords;

  @OneToOne(mappedBy = "word")
  private Audio audio;

}
