package com.leximemory.backend.controllers;

import com.leximemory.backend.controllers.dto.FlashCardDto;
import com.leximemory.backend.models.entities.FlashCard;
import com.leximemory.backend.services.FlashCardService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

/**
 * The type Flashcard controller.
 */
@RestController
@RequestMapping("users/{userId}/flashcards")
public class FlashcardController {

  private final FlashCardService flashCardService;

  /**
   * Instantiates a new Flashcard controller.
   *
   * @param flashCardService the flash card service
   */
  @Autowired
  public FlashcardController(FlashCardService flashCardService) {
    this.flashCardService = flashCardService;
  }

  /**
   * Gets user flashcards.
   *
   * @param userId the user id
   * @return the user flashcards
   */
  @GetMapping
  @ResponseStatus(HttpStatus.OK)
  public List<FlashCardDto> getUserFlashcards(
      @PathVariable Integer userId
  ) {
    List<FlashCard> flashCards = flashCardService.getAllByUserId(userId);
    return flashCards.stream().map(FlashCardDto::fromEntity).toList();
  }
}
