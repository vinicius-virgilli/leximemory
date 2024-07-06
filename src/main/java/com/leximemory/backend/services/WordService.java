package com.leximemory.backend.services;

import com.leximemory.backend.controllers.dto.worddto.WordCreationDto;
import com.leximemory.backend.models.entities.Audio;
import com.leximemory.backend.models.entities.Sentence;
import com.leximemory.backend.models.entities.User;
import com.leximemory.backend.models.entities.Word;
import com.leximemory.backend.models.repositories.WordRepository;
import com.leximemory.backend.services.exception.userwordexceptions.WordAlreadyExistsException;
import com.leximemory.backend.services.exception.wordexceptions.WordNotFoundException;
import com.leximemory.backend.util.TextHandler;
import jakarta.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

/**
 * The type Word service.
 */
@Service
public class WordService {

  private final WordRepository wordRepository;
  private final AudioService audioService;
  private final SentenceService sentenceService;
  private final UserService userService;

  /**
   * Instantiates a new Word service.
   *
   * @param wordRepository  the word repository
   * @param audioService    the audio service
   * @param sentenceService the sentence service
   * @param userService     the user service
   */
  @Autowired
  public WordService(
      WordRepository wordRepository,
      AudioService audioService,
      @Lazy
      SentenceService sentenceService,
      UserService userService
  ) {
    this.wordRepository = wordRepository;
    this.audioService = audioService;
    this.sentenceService = sentenceService;
    this.userService = userService;
  }

  /**
   * Create word.
   *
   * @param wordCreationDto the word
   * @return the word
   */
  @Transactional
  public Word createWord(WordCreationDto wordCreationDto) {
    List<Word> words = wordRepository.findAll();
    List<String> wordWords = words.stream().map(Word::getWord).toList();

    if (wordWords.contains(wordCreationDto.word())) {
      throw new WordAlreadyExistsException();
    }

    Word existingWord = words.stream()
        .filter(w -> w.getWord() != null && w.getWord().equalsIgnoreCase(wordCreationDto.word()))
        .findFirst()
        .orElse(null);

    Word newWord = new Word();
    if (existingWord != null) {
      newWord.setType(existingWord.getType());
      newWord.setMeaning(existingWord.getMeaning());
      newWord.setWordRank(existingWord.getWordRank());
      newWord.setRepetitions(existingWord.getRepetitions());

      newWord.setExempleSentences(new ArrayList<>(existingWord.getExempleSentences()));
      newWord.setQuestions(new ArrayList<>(existingWord.getQuestions()));
      newWord.setUserWords(new ArrayList<>(existingWord.getUserWords()));

      if (existingWord.getAudio() != null) {
        Audio newAudio = new Audio();
        newAudio.setAudio(existingWord.getAudio().getAudio());
        newAudio.setSentence(newWord.getAudio().getSentence());
        newWord.setAudio(newAudio);
      }
    } else {
      newWord.setType(TextHandler.getWordType(wordCreationDto.word()));
      newWord.setWordRank(wordCreationDto.rank() != null ? wordCreationDto.rank()
          : wordRepository.findAll().size() + 1);
      newWord.setRepetitions(
          wordCreationDto.repetitions() != null ? wordCreationDto.repetitions() : 0);
      newWord.setExempleSentences(new ArrayList<>());
      newWord.setQuestions(new ArrayList<>());
      newWord.setUserWords(new ArrayList<>());
      newWord.setAudio(null);
    }

    newWord.setWord(wordCreationDto.word());

    return wordRepository.save(newWord);
  }


  /**
   * Create word example sentences word.
   *
   * @param userId          the user id
   * @param wordId          the word id
   * @param wordCreationDto the word creation dto
   * @return the word
   */
  public Word createWordExampleSentences(
      Integer userId,
      Integer wordId,
      WordCreationDto wordCreationDto
  ) {
    User user = userService.getUserById(userId);
    Word word = getWordById(wordId);
    List<Sentence> exampleSentences = new ArrayList<>();

    for (int i = 0; i < wordCreationDto.sentences().size(); i++) {

      List<String> textSentence = TextHandler.splitTextIntoWords(
          wordCreationDto.sentences().get(i));
      String translation = wordCreationDto.translations().get(i);

      exampleSentences.add(sentenceService.createWordSentence(
          user, word, textSentence, translation));
    }

    word.setExempleSentences(exampleSentences);

    return wordRepository.save(word);
  }

  /**
   * Gets word by word.
   *
   * @param searchedWord the searched word
   * @return the word by word
   */
  public Word getWordByWord(String searchedWord) {
    Optional<Word> word = wordRepository.findByWord(searchedWord);
    if (word.isEmpty() || !searchedWord.equals(word.get().getWord())) {
      throw new WordNotFoundException();
    }

    return word.get();
  }

  /**
   * Gets word by word ignore case.
   *
   * @param searchedWord the searched word
   * @return the word by word ignore case
   */
  public Word getWordByWordIgnoreCase(String searchedWord) {
    return wordRepository.findByWord(searchedWord)
        .orElseThrow(WordNotFoundException::new);
  }

  /**
   * Create word audio word.
   *
   * @param wordId the word id
   * @return the word
   */
  public Word createWordAudio(Integer wordId) {
    Optional<Word> optionalWord = wordRepository.findById(wordId);
    if (optionalWord.isPresent()) {
      Word word = optionalWord.get();
      word.setAudio(audioService.createWordAudio(word));
      return word;
    } else {
      throw new WordNotFoundException();
    }
  }

  /**
   * Gets all words.
   *
   * @return the all words
   */
  @Transactional
  public List<Word> getAllWords() {
    return wordRepository.findAll();
  }

  /**
   * Gets word by id.
   *
   * @param id the id
   * @return the word by id
   */
  @Transactional
  public Word getWordById(Integer id) {
    return wordRepository.findById(id).orElseThrow(WordNotFoundException::new);
  }

}
