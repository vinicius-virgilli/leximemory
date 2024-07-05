package com.leximemory.backend.models.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * The type Audio.
 */
@Entity
@Table(name = "audios")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Audio {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;

  @Lob
  @Column(columnDefinition = "LONGBLOB")
  private byte[] audio;

  @OneToOne
  @JoinColumn(name = "word_id")
  private Word word;

  @OneToOne
  @JoinColumn(name = "question_id")
  private Question question;

  @OneToOne
  @JoinColumn(name = "sentence_id")
  private Sentence sentence;

}
