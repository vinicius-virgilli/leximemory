package com.leximemory.backend.services;

import com.leximemory.backend.controllers.dto.usertextdto.UserTextDto;
import com.leximemory.backend.models.entities.User;
import com.leximemory.backend.models.entities.UserText;
import com.leximemory.backend.models.enums.DifficultyLevel;
import com.leximemory.backend.models.repositories.UserTextRepository;
import com.leximemory.backend.services.exception.usertextexceptions.TextContentMustHaveMoreThanOneWordException;
import com.leximemory.backend.services.exception.usertextexceptions.UserTextNotFoundException;
import com.leximemory.backend.util.TextHandler;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * The type User text service.
 */
@Service
public class UserTextService {

  private final UserTextRepository userTextRepository;
  private final UserService userService;
  private final SentenceService sentenceService;

  /**
   * Instantiates a new User text service.
   *
   * @param userService        the user service
   * @param userTextRepository the user text repository
   * @param sentenceService    the sentence service
   */
  @Autowired
  public UserTextService(
      UserService userService,
      UserTextRepository userTextRepository,
      SentenceService sentenceService
  ) {
    this.userService = userService;
    this.userTextRepository = userTextRepository;
    this.sentenceService = sentenceService;
  }

  /**
   * Gets all by user id.
   *
   * @param userId the user id
   * @return the all by user id
   */
  public List<UserText> getAllByUserId(Integer userId) {
    return userTextRepository.findByUserId(userId);
  }

  /**
   * Gets user text by id.
   *
   * @param userId     the user id
   * @param userTextId the user text id
   * @return the user text by id
   */
  public UserText getUserTextById(
      Integer userId,
      Integer userTextId
  ) {
    User user = userService.getUserById(userId);
    return userTextRepository.findById(userTextId)
        .orElseThrow(UserTextNotFoundException::new);
  }

  /**
   * Create user text.
   *
   * @param userId      the user id
   * @param userTextDto the user text dto
   * @return the user text
   */
  public UserText createUserText(
      Integer userId,
      UserTextDto userTextDto
  ) {
    if (userTextDto.content().length() <= 1) {
      throw new TextContentMustHaveMoreThanOneWordException();
    }

    User user = userService.getUserById(userId);

    UserText newUserText = new UserText();

    newUserText.setUser(user);
    newUserText.setTitle(userTextDto.title());
    newUserText.setSubject(userTextDto.subject());
    newUserText.setDifficultyLevel(DifficultyLevel.MEDIUM);
    newUserText.setNewUserWordsCount(0);
    newUserText.setWordsTotal(TextHandler.countValidWords(userTextDto.content()));
    newUserText.setSentences(new ArrayList<>());

    UserText salvedUserText = userTextRepository.save(newUserText);
    user.getUserTexts().add(salvedUserText);

    List<List<String>> stringList = TextHandler.splitTextIntoSentences(userTextDto.content());

    stringList.forEach(
        strings -> sentenceService.createTextSentence(user, salvedUserText, strings
    ));

    return salvedUserText;
  }

  /**
   * Gets by id.
   *
   * @param userTextId the user text id
   * @return the by id
   */
  public UserText getById(Integer userTextId) {
    return userTextRepository.findById(userTextId)
        .orElseThrow(UserTextNotFoundException::new);
  }
}
