package com.leximemory.backend.services;

import com.leximemory.backend.controllers.dto.worddto.WordCreationDto;
import com.leximemory.backend.models.entities.Sentence;
import com.leximemory.backend.models.entities.User;
import com.leximemory.backend.models.entities.UserText;
import com.leximemory.backend.models.entities.UserWord;
import com.leximemory.backend.models.entities.Word;
import com.leximemory.backend.models.repositories.SentenceRepository;
import com.leximemory.backend.util.TextHandler;
import jakarta.transaction.Transactional;
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

    Sentence newSentece = createSentence(user, userWords, strings);
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
    List<UserWord> userWords = userWordService.getAllUserWords(user.getId());

    Sentence newSentence = createSentence(user, userWords, strings);

    newSentence.setTextSentence(TextHandler.buildSentence(newSentence.getSentence()));
    newSentence.setWord(word);
    newSentence.setTranslation(translation);
    newSentence.setTatoebaAudioId(tatoebaAudioId);

    return sentenceRepository.save(newSentence);
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

    List<Word> words = new ArrayList<>();
    List<String> userWordsWord = new ArrayList<>();
    userWords.forEach(
        userWord -> userWordsWord.add(userWord.getWord().getWord()));

    for (String str : strings) {

      if (userWordsWord.contains(str)) {

        Optional<UserWord> optionalUserWord = userWords.stream()
            .filter(uw -> uw.getWord().getWord().equals(str)).findFirst();

        if (optionalUserWord.isPresent()) {
          newSentence.getSentence().add(optionalUserWord.get());
          optionalUserWord.get().getSentences().add(newSentence);
        }

      } else {
        if (words.isEmpty()) {
          words = wordService.getAllWords();
        }

        UserWord newUserWord = createUserWord(str, words, user);

        newSentence.getSentence().add(newUserWord);

        newUserWord.getSentences().add(newSentence);

        userWords.add(newUserWord);
        userWordsWord.add(newUserWord.getWord().getWord());
      }
    }

    return newSentence;
  }

  /**
   * Create user word.
   *
   * @param string the string
   * @param words  the words
   * @param user   the user
   * @return the user word
   */
  public UserWord createUserWord(
      String string,
      List<Word> words,
      User user
  ) {
    Word salvedWord = findOrCreateWord(string, words);

    return userWordService.createUserWord(user, salvedWord);
  }

  /**
   * Create word.
   *
   * @param string the string
   * @param words  the words
   * @return the word
   */
  public Word findOrCreateWord(
      String string,
      List<Word> words
  ) {
    List<String> wordWords = words.stream()
        .map(Word::getWord).toList();

    if (wordWords.contains(string)) {
      Optional<Word> optionalWord =
          words.stream().filter(word -> word.getWord().equals(string)).findFirst();

      if (optionalWord.isPresent()) {
        return optionalWord.get();
      }
    }

    Word salvedWord = wordService.createWord(
        new WordCreationDto(
            string,
            null,
            null
        ),
        words
    );

    words.add(salvedWord);

    return salvedWord;
  }
}






















