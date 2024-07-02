package com.leximemory.backend.models.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * The type Sentence.
 */
@Entity
@Table(name = "sentences")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Sentence {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;

  @ManyToMany
  @JoinTable(
      name = "sentence_user_word",
      joinColumns = @JoinColumn(name = "sentence_id"),
      inverseJoinColumns = {
          @JoinColumn(name = "user_id", referencedColumnName = "user_id"),
          @JoinColumn(name = "word_id", referencedColumnName = "word_id")
      }
  )
  private List<UserWord> sentence;

  @ManyToOne
  @JoinColumn(name = "user_text_id")
  private UserText userText;

  @OneToOne(mappedBy = "sentence")
  private Audio audio;

}
