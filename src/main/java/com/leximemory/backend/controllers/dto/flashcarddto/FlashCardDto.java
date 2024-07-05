package com.leximemory.backend.controllers.dto.flashcarddto;

import com.leximemory.backend.models.entities.FlashCard;


/**
 * The type Flash card dto.
 */
public record FlashCardDto(
    Integer id,
    String front,
    String verse
) {

  /**
   * To entity flash card.
   *
   * @return the flash card
   */
  public FlashCard toEntity() {
    FlashCard flashCard = new FlashCard();
    flashCard.setFront(this.front);
    flashCard.setVerse(this.verse);
    return flashCard;
  }

  /**
   * From entity flash card dto.
   *
   * @param flashCard the flash card
   * @return the flash card dto
   */
  public static FlashCardDto fromEntity(FlashCard flashCard) {
    return new FlashCardDto(
        flashCard.getId(),
        flashCard.getFront(),
        flashCard.getVerse()
    );
  }
}
