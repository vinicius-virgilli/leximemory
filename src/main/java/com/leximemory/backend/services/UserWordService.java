package com.leximemory.backend.services;

import com.leximemory.backend.models.entities.User;
import com.leximemory.backend.models.entities.UserWord;
import com.leximemory.backend.models.entities.Word;
import com.leximemory.backend.models.entities.id.UserWordId;
import com.leximemory.backend.models.repositories.UserWordRepository;
import com.leximemory.backend.services.exception.flashcardexceptions.FlashCardAlreadyExistsException.UserWordAlreadyExistsException;
import com.leximemory.backend.services.exception.userwordexceptions.UserWordNotFoundException;
import com.leximemory.backend.util.Calculator;
import java.time.LocalDateTime;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * The type User word service.
 */
@Service
public class UserWordService {

  private final UserWordRepository userWordRepository;
  private final UserService userService;
  private final WordService wordService;

  /**
   * Instantiates a new User word service.
   *
   * @param userWordRepository the user word repository
   * @param userService        the user service
   * @param wordService        the word service
   */
  @Autowired
  public UserWordService(
      UserWordRepository userWordRepository,
      UserService userService,
      WordService wordService
  ) {
    this.userWordRepository = userWordRepository;
    this.userService = userService;
    this.wordService = wordService;
  }

  /**
   * Gets all user word by user id.
   *
   * @param userId the user id
   * @return the all user word by user id
   */
  public List<UserWord> getAllUserWords(Integer userId) {
    return userWordRepository.findByUserId(userId);
  }

  /**
   * Gets user word by id.
   *
   * @param id the id
   * @return the user word by id
   */
  public UserWord getUserWordById(UserWordId id) {
    return userWordRepository.findById(id).orElseThrow(UserWordNotFoundException::new);
  }

  /**
   * Create userWord.
   *
   * @param userWord the user word
   * @return the user word
   */
  public UserWord createUserWord(UserWord userWord) {
    try {
      getUserWordById(userWord.getId());
      throw new UserWordAlreadyExistsException();
    } catch (UserWordNotFoundException e) {
      User user = userService.getUserById(userWord.getId().getUserId());
      Word word = wordService.getWordById(userWord.getId().getWordId());
      userWord.setUser(user);
      userWord.setWord(word);
      userWord.setRegistrationDate(LocalDateTime.now());
      userWord.setLastRevisionDate(LocalDateTime.now());
      userWord.setTemperature(Calculator.getTemp(userWord));
      return userWordRepository.save(userWord);
    }
  }
}
