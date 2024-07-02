package com.leximemory.backend.services;

import com.leximemory.backend.models.entities.Word;
import com.leximemory.backend.models.repositories.WordRepository;
import com.leximemory.backend.services.exception.userwordexceptions.WordAlreadyExistsException;
import com.leximemory.backend.services.exception.wordexceptions.WordNotFoundException;
import com.leximemory.backend.util.AudioHandler;
import jakarta.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * The type Word service.
 */
@Service
public class WordService {

  private final WordRepository wordRepository;
  private final AudioService audioService;

  /**
   * Instantiates a new Word service.
   *
   * @param wordRepository the word repository
   * @param audioService   the audio service
   */
  @Autowired
  public WordService(
      WordRepository wordRepository,
      AudioService audioService
  ) {
    this.wordRepository = wordRepository;
    this.audioService = audioService;
  }

  /**
   * Create word.
   *
   * @param newWord the word
   * @return the word
   */
  @Transactional
  public Word createWord(Word newWord) {
    List<Word> words = wordRepository.findAll();
    Optional<Word> existingWord = words
        .stream()
        .filter(word -> word.getWord().equals(newWord.getWord()))
        .findFirst();
    if (existingWord.isPresent()) {
      throw new WordAlreadyExistsException();
    }

    newWord.setAudio(audioService.createWordAudio(newWord));

    return wordRepository.save(newWord);
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

  /**
   * Gets word by word.
   *
   * @param word the word
   * @return the word by word
   */
  public Word getWordByWord(String word) {
    return wordRepository.findByWord(word).orElseThrow(WordNotFoundException::new);
  }

}
