package com.leximemory.backend.services;

import com.leximemory.backend.controllers.dto.userworddto.UserWordDto;
import com.leximemory.backend.controllers.dto.worddto.WordCreationDto;
import com.leximemory.backend.models.entities.Audio;
import com.leximemory.backend.models.entities.Sentence;
import com.leximemory.backend.models.entities.User;
import com.leximemory.backend.models.entities.UserText;
import com.leximemory.backend.models.entities.UserWord;
import com.leximemory.backend.models.entities.Word;
import com.leximemory.backend.models.entities.id.UserWordId;
import com.leximemory.backend.models.enums.DifficultyLevel;
import com.leximemory.backend.models.enums.Temperature;
import com.leximemory.backend.models.enums.WordType;
import com.leximemory.backend.models.repositories.AudioRepository;
import com.leximemory.backend.models.repositories.SentenceRepository;
import com.leximemory.backend.models.repositories.UserWordRepository;
import com.leximemory.backend.services.exception.wordexceptions.WordNotFoundException;
import com.leximemory.backend.util.TextHandler;
import jakarta.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.services.polly.endpoints.internal.Value.Int;

/**
 * The type Sentence service.
 */
@Service
public class SentenceService {

  private final SentenceRepository sentenceRepository;
  private final UserWordService userWordService;
  private final UserWordRepository userWordRepository;
  private final WordService wordService;
  private final AudioRepository audioRepository;


  /**
   * Instantiates a new Sentence service.
   *
   * @param sentenceRepository the sentence repository
   * @param userWordService    the user word service
   * @param userWordRepository the user word repository
   * @param wordService        the word service
   * @param userService        the user service
   * @param audioRepository    the audio repository
   */
  @Autowired
  public SentenceService(
      SentenceRepository sentenceRepository,
      UserWordService userWordService,
      UserWordRepository userWordRepository,
      WordService wordService,
      UserService userService,
      AudioRepository audioRepository
  ) {
    this.sentenceRepository = sentenceRepository;
    this.userWordService = userWordService;
    this.wordService = wordService;
    this.userWordRepository = userWordRepository;
    this.audioRepository = audioRepository;
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

  /**
   * Create text sentence.
   *
   * @param user     the user
   * @param userText the user text
   * @param strings  the strings
   */
  @Transactional
  public void createTextSentence(
      User user,
      UserText userText,
      List<String> strings
  ) {
    List<UserWord> userWords = userWordService.getAllUserWords(user.getId());

    userText.setNewUserWordsCount(
        userText.getNewUserWordsCount() + TextHandler.countNewValidWords(
            userWords, strings
        ));

    Sentence newSentece = createSentence(user, userWords, strings);
    newSentece.setUserText(userText);

    sentenceRepository.save(newSentece);
  }

  /**
   * Create word sentence sentence.
   *
   * @param user        the user
   * @param word        the word
   * @param strings     the strings
   * @param translation the translation
   * @return the sentence
   */
  public Sentence createWordSentence(
      User user,
      Word word,
      List<String> strings,
      String translation
  ) {
    List<UserWord> userWords = userWordService.getAllUserWords(user.getId());

    Sentence newSentence = createSentence(user, userWords, strings);

    newSentence.setWord(word);
    newSentence.setTranslation(translation);

    Sentence salvedSentence = sentenceRepository.save(newSentence);

    Audio audio = new Audio();
    audio.setSentence(salvedSentence);
    Audio salvedAudio = audioRepository.save(audio);
    newSentence.setAudio(audio);

    return sentenceRepository.save(salvedSentence);
  }

  /**
   * Create sentence.
   *
   * @param user      the user
   * @param userWords the user words
   * @param strings   the strings
   * @return the sentence
   */
  @Transactional
  public Sentence createSentence(
      User user,
      List<UserWord> userWords,
      List<String> strings
  ) {
    Sentence newSentence = new Sentence();
    newSentence.setSentence(new ArrayList<>());

    for (String str : strings) {
      List<String> userWordsWord = new ArrayList<>();

      if (!userWords.isEmpty()) {
        userWordsWord = userWords.stream().map(uw -> uw.getWord().getWord()).toList();
      }

      if (userWordsWord.contains(str)) {

        Optional<UserWord> optionalUserWord = userWords.stream()
            .filter(uw -> uw.getWord().getWord().equals(str)).findFirst();

        if (optionalUserWord.isPresent()) {
          newSentence.getSentence().add(optionalUserWord.get());
          optionalUserWord.get().getSentences().add(newSentence);
        }

      } else {
        UserWord newUserWord = createWordAndUserWord(
            user, str);
        newSentence.getSentence().add(newUserWord);

        if (newUserWord.getSentences() == null) {
          newUserWord.setSentences(new ArrayList<>());
        }
        newUserWord.getSentences().add(newSentence);

        userWords.add(newUserWord);
      }
    }

    return newSentence;
  }

  /**
   * Create word and user word.
   *
   * @param user   the user
   * @param string the string
   * @return the user word
   */
  @Transactional
  public UserWord createWordAndUserWord(
      User user,
      String string
  ) {
    Word salvedWord = wordService.createWord(
        new WordCreationDto(
            string,
            null,
            null,
            new ArrayList<>(),
            new ArrayList<>())
    );

    UserWordId userWordId = new UserWordId(user.getId(), salvedWord.getId());

    UserWord newUserWord = new UserWordDto(
        userWordId,
        salvedWord.getWord(),
        LocalDateTime.now(),
        LocalDateTime.now(),
        0,
        DifficultyLevel.MEDIUM,
        Temperature.SEVEN
    ).toEntity(userWordId);

    newUserWord.setUser(user);
    newUserWord.setWord(salvedWord);

    return userWordRepository.save(newUserWord);
  }
}






















