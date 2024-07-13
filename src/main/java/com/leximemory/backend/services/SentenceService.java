package com.leximemory.backend.services;

import com.leximemory.backend.controllers.dto.sentencedto.SentencesCreationDto;
import com.leximemory.backend.controllers.dto.worddto.WordCreationDto;
import com.leximemory.backend.models.entities.Sentence;
import com.leximemory.backend.models.entities.User;
import com.leximemory.backend.models.entities.UserText;
import com.leximemory.backend.models.entities.UserWord;
import com.leximemory.backend.models.entities.Word;
import com.leximemory.backend.models.repositories.SentenceRepository;
import com.leximemory.backend.services.exception.wordexceptions.WordNotFoundException;
import com.leximemory.backend.util.TextHandler;
import jakarta.transaction.Transactional;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * The type Sentence service.
 */
@Service
public class SentenceService {

  private final SentenceRepository sentenceRepository;
  private final UserWordService userWordService;
  private final WordService wordService;


  /**
   * Instantiates a new Sentence service.
   *
   * @param sentenceRepository the sentence repository
   * @param userWordService    the user word service
   * @param wordService        the word service
   */
  @Autowired
  public SentenceService(
      SentenceRepository sentenceRepository,
      UserWordService userWordService,
      WordService wordService
  ) {
    this.sentenceRepository = sentenceRepository;
    this.userWordService = userWordService;
    this.wordService = wordService;
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

    Sentence newSentece = createSentence(user, strings);
    newSentece.setUserText(userText);

    sentenceRepository.save(newSentece);
  }

  /**
   * Create word sentence sentence.
   *
   * @param user           the user
   * @param word           the word
   * @param strings        the strings
   * @param translation    the translation
   * @param tatoebaAudioId the tatoeba audio id
   * @return the sentence
   */
  @Transactional
  public Sentence createWordSentence(
      User user,
      Word word,
      List<String> strings,
      String translation,
      Integer tatoebaAudioId
  ) {

    Sentence newSentence = createSentence(user, strings);

    newSentence.setTextSentence(TextHandler.buildSentence(newSentence.getSentence()));
    newSentence.setWord(word);
    newSentence.setTranslation(translation);
    newSentence.setTatoebaAudioId(tatoebaAudioId);

    return sentenceRepository.save(newSentence);
  }

  /**
   * Create sentence sentence.
   *
   * @param user    the user
   * @param strings the strings
   * @return the sentence
   */
  @Transactional
  public Sentence createSentence(
      User user,
      List<String> strings
  ) {
    Sentence newSentence = new Sentence();
    newSentence.setSentence(new ArrayList<>());
    List<Word> words = new ArrayList<>();

    for (String str : strings) {

      List<UserWord> userWordss = userWordService.findByUserAndWordWord(user, str);

      if (!userWordss.isEmpty()) {
        newSentence.getSentence().add(userWordss.get(0));
        userWordss.get(0).getSentences().add(newSentence);

      } else {
        if (words.isEmpty()) {
          words = wordService.getWordByWordIgnoreCase(str);
        }

        UserWord newUserWord = createUserWord(str, user);

        newSentence.getSentence().add(newUserWord);

        newUserWord.getSentences().add(newSentence);

        words.add(newUserWord.getWord());
      }
    }

    return newSentence;
  }


  /**
   * Create user word.
   *
   * @param string the string
   * @param user   the user
   * @return the user word
   */
  public UserWord createUserWord(
      String string,
      User user
  ) {
    Word salvedWord = findOrCreateWord(string);

    return userWordService.createUserWord(user, salvedWord);
  }

  /**
   * Create word.
   *
   * @param string the string
   * @return the word
   */
  public Word findOrCreateWord(
      String string
  ) {
    Word optionalWord;

    try {
      optionalWord = wordService.getWordByWord(string);
    } catch (WordNotFoundException e) {
      optionalWord = null;
    }

    if (optionalWord != null) {
      return optionalWord;
    }

    return wordService.createWord(new WordCreationDto(string, null, null));
  }
}






















