package com.leximemory.backend.models.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
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
import lombok.EqualsAndHashCode;
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
  private Integer wordRank;
  private Integer repetitions;

  @OneToMany(mappedBy = "word", fetch = FetchType.LAZY)
  @JsonIgnore
  @EqualsAndHashCode.Exclude
  private List<Sentence> exempleSentences;

  @OneToMany(mappedBy = "word", fetch = FetchType.LAZY)
  @JsonIgnore
  private List<Question> questions;

  @OneToMany(mappedBy = "word", fetch = FetchType.LAZY)
  @JsonIgnore
  private List<UserWord> userWords;

  @OneToOne(mappedBy = "word")
  @JsonIgnore
  private Audio audio;

}
