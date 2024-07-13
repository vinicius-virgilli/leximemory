package com.leximemory.backend.models.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
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
@Builder
public class Sentence {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @JsonIgnore
  private Integer id;
  @Lob
  @Column(columnDefinition = "LONGBLOB")
  private String textSentence;
  @Lob
  @Column(columnDefinition = "LONGBLOB")
  private String translation;
  private Integer tatoebaAudioId;

  @ManyToMany
  @JoinTable(
      name = "sentence_user_word",
      joinColumns = @JoinColumn(name = "sentence_id"),
      inverseJoinColumns = {
          @JoinColumn(name = "user_id", referencedColumnName = "user_id"),
          @JoinColumn(name = "word_id", referencedColumnName = "word_id")
      }
  )
  @JsonIgnore
  private List<UserWord> sentence;

  @ManyToOne
  @JoinColumn(name = "word_id")
  @JsonIgnore
  private Word word;

  @ManyToOne
  @JoinColumn(name = "user_text_id")
  @JsonIgnore
  private UserText userText;

  @OneToOne(mappedBy = "sentence")
  @JsonIgnore
  private Audio audio;

}
