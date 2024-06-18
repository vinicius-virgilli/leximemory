package com.leximemory.backend.services;

import com.leximemory.backend.exception.FlashCardAlreadyExistsException;
import com.leximemory.backend.models.entities.FlashCard;
import com.leximemory.backend.models.entities.UserWord;
import com.leximemory.backend.models.entities.id.UserWordId;
import com.leximemory.backend.models.repositories.FlashCardRepository;
import java.util.List;
import java.util.Objects;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * The type Flash card service.
 */
@Service
public class FlashCardService {

  private final UserWordService userWordService;
  private final FlashCardRepository flashCardRepository;

  /**
   * Instantiates a new Flash card service.
   *
   * @param userWordService     the user word service
   * @param flashCardRepository the flash card repository
   */
  @Autowired
  public FlashCardService(
      UserWordService userWordService,
      FlashCardRepository flashCardRepository) {
    this.userWordService = userWordService;
    this.flashCardRepository = flashCardRepository;
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
   * Gets by user id.
   *
   * @param userId the user id
   * @return the by user id
   */
  public List<FlashCard> getAllByUserId(Integer userId) {
    List<FlashCard> flashCards = flashCardRepository.findAll();
    return flashCards.stream().filter(
        flashCard -> Objects.equals(flashCard.getUserWord().getId().getUserId(), userId)
    ).toList();
  }
}














