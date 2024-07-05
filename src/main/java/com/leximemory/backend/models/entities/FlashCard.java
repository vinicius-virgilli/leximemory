package com.leximemory.backend.models.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinColumns;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * The type Flash card.
 */
@Entity
@Table(name = "flash_cards")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class FlashCard {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;
  private String front;
  private String verse;

  @OneToOne
  @JoinColumns({
      @JoinColumn(name = "user_id", referencedColumnName = "user_id"),
      @JoinColumn(name = "word_id", referencedColumnName = "word_id")
  })
  private UserWord userWord;

  @ManyToOne
  @JoinColumn(
      name = "user_id",
      insertable = false,
      updatable = false
  )
  private User user;

  @ManyToMany(mappedBy = "flashcards", fetch = FetchType.LAZY)
  private List<Review> reviews;
}
