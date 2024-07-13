package com.leximemory.backend.services;

import com.leximemory.backend.controllers.dto.sentencedto.WordSentenceDto;
import com.leximemory.backend.controllers.dto.sentencedto.WordSentencesDto;
import com.leximemory.backend.controllers.dto.worddto.WordCompleteDto;
import com.leximemory.backend.controllers.dto.worddto.WordCreationDto;
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
   * Create complete word.
   *
   * @param wordCompleteDto the word
   * @return the word
   */

  public Word createCompleteWord(WordCompleteDto wordCompleteDto) {
    Word newWord = createWord(new WordCreationDto(
        wordCompleteDto.word(),
        wordCompleteDto.wordRank(),
        wordCompleteDto.repetitions()
    ));

    createWordExampleSentences(1, newWord.getId(), wordCompleteDto.exampleSentences());

    return wordRepository.save(newWord);
  }

  /**
   * Create word.
   *
   * @param wordCreationDto the word creation dto
   * @return the word
   */
  public Word createWord(WordCreationDto wordCreationDto) {
    List<Word> words = wordRepository.findByWordIgnoreCase(wordCreationDto.word());

    return createWord(wordCreationDto, words);
  }

  /**
   * Create word.
   *
   * @param wordCreationDto the wor
   * @param words           the words
   * @return the word
   */
  @Transactional
  public Word createWord(
      WordCreationDto wordCreationDto,
      List<Word> words
  ) {
    List<String> wordWords = words.stream().map(Word::getWord).toList();

    if (wordWords.contains(wordCreationDto.word())) {
      throw new WordAlreadyExistsException();
    }

    Word existingWord = words.stream()
        .filter(w -> w.getWord() != null && w.getWord().equalsIgnoreCase(wordCreationDto.word()))
        .findFirst()
        .orElse(null);

    Word newWord = new Word();
    Word salvedWord = new Word();

    if (existingWord != null) {
      newWord.setType(existingWord.getType());
      newWord.setMeaning(existingWord.getMeaning());
      newWord.setWordRank(existingWord.getWordRank());
      newWord.setRepetitions(existingWord.getRepetitions());

      newWord.setExempleSentences(new ArrayList<>(existingWord.getExempleSentences()));
      newWord.setQuestions(new ArrayList<>(existingWord.getQuestions()));
      newWord.setUserWords(new ArrayList<>(existingWord.getUserWords()));

      if (existingWord.getAudio() != null) {
        salvedWord = wordRepository.save(newWord);

        newWord.setAudio(audioService.createAudioCopy(existingWord.getAudio(), salvedWord));
      }
    } else {
      newWord.setType(TextHandler.getWordType(wordCreationDto.word()));
      newWord.setWordRank(wordCreationDto.rank() != null ? wordCreationDto.rank()
          : words.size() + 1);
      newWord.setRepetitions(
          wordCreationDto.repetitions() != null ? wordCreationDto.repetitions() : 0);
      newWord.setExempleSentences(new ArrayList<>());
      newWord.setQuestions(new ArrayList<>());
      newWord.setUserWords(new ArrayList<>());

      salvedWord = wordRepository.save(newWord);

      newWord.setAudio(audioService.createInitialWordAudio(salvedWord));
    }

    newWord.setWord(wordCreationDto.word());

    return wordRepository.save(salvedWord);
  }


  /**
   * Create word example sentences word.
   *
   * @param userId    the user id
   * @param wordId    the word id
   * @param sentences the sentences
   * @return the word
   */
  @Transactional
  public Word createWordExampleSentences(
      Integer userId,
      Integer wordId,
      WordSentencesDto sentences
  ) {
    User user = userService.getUserById(userId);
    Word word = getWordById(wordId);
    List<Sentence> exampleSentences = new ArrayList<>();

    for (WordSentenceDto sentence : sentences.sentences()) {

      List<String> textSentence = TextHandler.splitTextIntoWords(sentence.textSentence());

      exampleSentences.add(sentenceService.createWordSentence(
          user, word, textSentence, sentence.translation(), sentence.tatoebaAudioId()));
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
    return wordRepository.findByWordIgnoreCase(searchedWord)
        .stream()
        .filter(w -> w.getWord().equalsIgnoreCase(searchedWord))
        .findFirst()
        .orElseThrow(WordNotFoundException::new);
  }

  /**
   * Gets word by word ignore case.
   *
   * @param searchedWord the searched word
   * @return the word by word ignore case
   */
  public List<Word> getWordByWordIgnoreCase(String searchedWord) {
    return wordRepository.findByWordIgnoreCase(searchedWord);
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
