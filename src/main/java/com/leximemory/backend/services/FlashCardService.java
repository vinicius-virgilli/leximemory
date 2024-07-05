package com.leximemory.backend.services;

import com.leximemory.backend.models.entities.FlashCard;
import com.leximemory.backend.models.entities.User;
import com.leximemory.backend.models.entities.UserWord;
import com.leximemory.backend.models.entities.id.UserWordId;
import com.leximemory.backend.models.repositories.FlashCardRepository;
import com.leximemory.backend.services.exception.flashcardexceptions.FlashCardAlreadyExistsException;
import com.leximemory.backend.services.exception.flashcardexceptions.FlashcardNotFoundException;
import java.util.List;
import java.util.Objects;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * The type Flash card service.
 */
@Service
public class FlashCardService {

  private final UserService userService;
  private final UserWordService userWordService;
  private final FlashCardRepository flashCardRepository;

  /**
   * Instantiates a new Flash card service.
   *
   * @param userWordService     the user word service
   * @param flashCardRepository the flash card repository
   * @param userService         the user service
   */
  @Autowired
  public FlashCardService(
      UserWordService userWordService,
      FlashCardRepository flashCardRepository,
      UserService userService
  ) {
    this.userWordService = userWordService;
    this.flashCardRepository = flashCardRepository;
    this.userService = userService;
  }

  /**
   * Create flash card.
   *
   * @param newFlashCard the new flash card
   * @param userWordId   the user word id
   * @return the flash card
   */
  public FlashCard createFlashCard(FlashCard newFlashCard, UserWordId userWordId) {
    UserWord userWord = userWordService.getUserWordById(userWordId);

    FlashCard flashCard = userWord.getFlashCard();
    if (Objects.isNull(flashCard)) {
      newFlashCard.setUserWord(userWord);
      User user = userService.getUserById(userWordId.getUserId());
      newFlashCard.setUser(user);

      return flashCardRepository.save(newFlashCard);
    }

    throw new FlashCardAlreadyExistsException();
  }

  /**
   * Gets by user word id.
   *
   * @param userWordId the user word id
   * @return the by user word id
   */
  public FlashCard getByUserWordId(UserWordId userWordId) {
    UserWord userWord = userWordService.getUserWordById(userWordId);
    return userWord.getFlashCard();
  }

  /**
   * Gets all by user id.
   *
   * @param userId the user id
   * @return the all by user id
   */
  public List<FlashCard> getAllByUserId(Integer userId) {
    User user = userService.getUserById(userId);
    return user.getFlashCards();
  }

  /**
   * Gets by id.
   *
   * @param flashcardId the flashcard id
   * @return the by id
   */
  public FlashCard getById(Integer flashcardId) {
    return flashCardRepository.findById(flashcardId)
        .orElseThrow(FlashcardNotFoundException::new);
  }
}














