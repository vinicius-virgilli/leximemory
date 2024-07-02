package com.leximemory.backend.services;

import com.leximemory.backend.controllers.dto.UserWordDto;
import com.leximemory.backend.models.entities.Sentence;
import com.leximemory.backend.models.entities.User;
import com.leximemory.backend.models.entities.UserText;
import com.leximemory.backend.models.entities.UserWord;
import com.leximemory.backend.models.entities.Word;
import com.leximemory.backend.models.entities.id.UserWordId;
import com.leximemory.backend.models.enums.DifficultyLevel;
import com.leximemory.backend.models.enums.Temperature;
import com.leximemory.backend.models.enums.WordType;
import com.leximemory.backend.models.repositories.SentenceRepository;
import com.leximemory.backend.models.repositories.UserWordRepository;
import com.leximemory.backend.services.exception.wordexceptions.WordNotFoundException;
import com.leximemory.backend.util.ProvisionalGptChatApi;
import com.leximemory.backend.util.TextHandler;
import jakarta.transaction.Transactional;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * The type Sentence service.
 */
@Service
public class SentenceService {

  private final SentenceRepository sentenceRepository;
  private final UserWordService userWordService;
  private final UserWordRepository userWordRepository;
  private final WordService wordService;


  /**
   * Instantiates a new Sentence service.
   *
   * @param sentenceRepository the sentence repository
   * @param userWordService    the user word service
   * @param userWordRepository the user word repository
   * @param wordService        the word service
   */
  @Autowired
  public SentenceService(
      SentenceRepository sentenceRepository,
      UserWordService userWordService,
      UserWordRepository userWordRepository,
      WordService wordService,
      UserService userService
  ) {
    this.sentenceRepository = sentenceRepository;
    this.userWordService = userWordService;
    this.wordService = wordService;
    this.userWordRepository = userWordRepository;
  }


  /**
   * Create sentence.
   *
   * @param userText the user text
   * @param strings  the strings
   */
  @Transactional
  public void createSentence(
      UserText userText,
      List<String> strings
  ) {
    Sentence newSentence = new Sentence();
    newSentence.setSentence(new ArrayList<>());

    List<UserWord> userWords = userWordService.getAllUserWords(userText.getUser().getId());
    List<String> userWordsWord = new ArrayList<>();
    if (!userWords.isEmpty()) {
      userWordsWord = userWords.stream().map(uw -> uw.getWord().getWord().toLowerCase()).toList();
    }

    for (String str : strings) {

      if (userWordsWord.contains(str.toLowerCase())) {

        Optional<UserWord> optionalUserWord = userWords.stream()
            .filter(uw -> uw.getWord().getWord().equalsIgnoreCase(str)).findFirst();

        if (optionalUserWord.isPresent()) {
          newSentence.getSentence().add(optionalUserWord.get());
          optionalUserWord.get().getSentences().add(newSentence);
        }

      } else if (TextHandler.filterValidWords(strings).contains(str)) {
        UserWord newUserWord = createWordAndUserWord(
            userText.getUser(), str, WordType.REAL_WORD);
        newSentence.getSentence().add(newUserWord);
        if (newUserWord.getSentences() == null) {
          newUserWord.setSentences(new ArrayList<>());
        }
        newUserWord.getSentences().add(newSentence);

        userText.setNewUserWordsCount(userText.getNewUserWordsCount() + 1);

      } else {
        UserWord newUserWord = createWordAndUserWord(userText.getUser(), str, WordType.NO_WORD);
        newSentence.getSentence().add(newUserWord);
        if (newUserWord.getSentences() == null) {
          newUserWord.setSentences(new ArrayList<>());
        }
        newUserWord.getSentences().add(newSentence);
      }
    }

    newSentence.setUserText(userText);
    sentenceRepository.save(newSentence);
  }

  /**
   * Create word and user word.
   *
   * @param user     the user
   * @param string   the string
   * @param wordType the word type
   * @return the user word
   */
  public UserWord createWordAndUserWord(
      User user,
      String string,
      WordType wordType
  ) {
    Word word = new Word();
    word.setWord(" ");
    Integer wordId = 99999;
    try {
      word = wordService.getWordByWord(string);
      wordId = word.getId();
    } catch (WordNotFoundException e) {
      word = ProvisionalGptChatApi.generateWord(string, wordType);
      wordId = wordService.createWord(word).getId();
    }
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

    return userWordRepository.save(newUserWord);
  }

  /**
   * Gets all by user text id.
   *
   * @param userTextId the user text id
   * @return the all by user text id
   */
  public List<Sentence> getAllByUserTextId(Integer userTextId) {
    return sentenceRepository.findByUserTextId(userTextId);
  }
}






















