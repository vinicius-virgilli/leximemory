package com.leximemory.backend.services;

import com.leximemory.backend.controllers.dto.UserTextDto;
import com.leximemory.backend.controllers.dto.UserWordDto;
import com.leximemory.backend.exception.usertextexceptions.TextContentMustHaveMoreThanOneWordException;
import com.leximemory.backend.exception.usertextexceptions.UserTextNotFoundException;
import com.leximemory.backend.exception.wordexceptions.WordNotFoundException;
import com.leximemory.backend.models.entities.User;
import com.leximemory.backend.models.entities.UserText;
import com.leximemory.backend.models.entities.UserWord;
import com.leximemory.backend.models.entities.Word;
import com.leximemory.backend.models.entities.id.UserWordId;
import com.leximemory.backend.models.enums.DifficultyLevel;
import com.leximemory.backend.models.enums.Temperature;
import com.leximemory.backend.models.enums.WordType;
import com.leximemory.backend.models.repositories.UserTextRepository;
import com.leximemory.backend.models.repositories.UserWordRepository;
import com.leximemory.backend.util.ProvisionalGptChatApi;
import com.leximemory.backend.util.TextHandler;
import jakarta.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * The type User text service.
 */
@Service
public class UserTextService {

  private final UserService userService;
  private final UserTextRepository userTextRepository;
  private final UserWordService userWordService;
  private final UserWordRepository userWordRepository;
  private final WordService wordService;

  /**
   * Instantiates a new User text service.
   *
   * @param userService        the user service
   * @param userTextRepository the user text repository
   * @param userWordService    the user word service
   * @param wordService        the word service
   * @param userWordRepository the user word repository
   */
  @Autowired
  public UserTextService(
      UserService userService,
      UserTextRepository userTextRepository,
      UserWordService userWordService,
      WordService wordService,
      UserWordRepository userWordRepository
  ) {
    this.userService = userService;
    this.userTextRepository = userTextRepository;
    this.userWordService = userWordService;
    this.wordService = wordService;
    this.userWordRepository = userWordRepository;
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
    List<String> strings = TextHandler.splitTextIntoWords(userTextDto.content());
    List<String> validWords = TextHandler.filterValidWords(strings);
    newUserText.setUser(user);
    List<UserWord> userWords = userWordService.getAllUserWords(userId);

    newUserText.setUser(user);
    newUserText.setWordsTotal(validWords.size());
    newUserText.setTitle(userTextDto.title());
    newUserText.setSubject(userTextDto.subject());
    newUserText.setDifficultyLevel(DifficultyLevel.MEDIUM);
    newUserText.setNewUserWordsCount(
        countNewUserWords(validWords, userWords)
    );
    newUserText.setContent(completeTextContent(
        user,
        newUserText,
        strings,
        validWords,
        userWords
    ));

    return userTextRepository.save(newUserText);
  }


  /**
   * Complete text content list.
   *
   * @param user        the user
   * @param newUserText the new user text
   * @param strings     the strings
   * @param validWords  the valid words
   * @param userWords   the user words
   * @return the list
   */
  @Transactional
  public List<UserWord> completeTextContent(
      User user,
      UserText newUserText,
      List<String> strings,
      List<String> validWords,
      List<UserWord> userWords
  ) {
    List<UserWord> content = new ArrayList<>();

    List<String> userWordsWord = userWords.stream().map(
        userWord -> userWord.getWord().getWord().toLowerCase()
    ).toList();

    for (String str : strings) {

      if (userWordsWord.contains(str.toLowerCase())) {
        Optional<UserWord> optionalUserWord = userWords.stream()
            .filter(userWord -> userWord.getWord().getWord().equalsIgnoreCase(str))
            .findFirst();

        if (optionalUserWord.isPresent()) {
          content.add(optionalUserWord.get());
          optionalUserWord.get().getUserTexts().add(newUserText);
        }

      } else if (validWords.contains(str)) {
        UserWord newUserWord = createWordAndUserWord(user, str, WordType.REAL_WORD);
        content.add(newUserWord);
        newUserWord.getUserTexts().add(newUserText);

      } else {
        UserWord newUserWord = createWordAndUserWord(user, str, WordType.NO_WORD);
        content.add(newUserWord);
        if (newUserWord.getUserTexts() == null) {
          newUserWord.setUserTexts(new ArrayList<>());
        }
        newUserWord.getUserTexts().add(newUserText);
      }
    }

    return content;
  }

  /**
   * Count new user words integer.
   *
   * @param validWords the valid words
   * @param userWords  the user words
   * @return the integer
   */
  public Integer countNewUserWords(
      List<String> validWords,
      List<UserWord> userWords
  ) {
    List<String> userWordsWord = userWords.stream().map(
        userWord -> userWord.getWord().getWord().toLowerCase()
    ).toList();
    Integer count = 0;
    for (String srt : validWords) {
      if (!userWordsWord.contains(srt.toLowerCase())) {
        count++;
      }
    }
    return count;
  }


  /**
   * Create word and user word.
   *
   * @param user   the user
   * @param string the word
   * @return the user word
   */
  public UserWord createWordAndUserWord(
      User user,
      String string,
      WordType wordType
  ) {
    // System.out.println(user + "\n\n string = " + string);
    Word word = null;
    Integer wordId = 99999;
    UserWord userWord;
    try {
      word = wordService.getWordByWord(string);
      wordId = word.getId();
    } catch (WordNotFoundException e) {
      // implement error handling
      word = ProvisionalGptChatApi.generateWord(string, wordType);
      wordId = wordService.createWord(word).getId();
    } finally {
      UserWordId userWordId = new UserWordId(user.getId(), wordId);
      UserWord newUserWord = new UserWordDto(
          userWordId,
          LocalDateTime.now(),
          LocalDateTime.now(),
          0,
          DifficultyLevel.MEDIUM,
          Temperature.SEVEN
      ).toEntity(userWordId);
      newUserWord.setUser(user);
      newUserWord.setWord(word);

      userWord = userWordRepository.save(newUserWord);
    }
    return userWord;
  }
}
