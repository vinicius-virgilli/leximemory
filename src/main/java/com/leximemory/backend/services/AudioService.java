package com.leximemory.backend.services;

import com.leximemory.backend.models.entities.Audio;
import com.leximemory.backend.models.entities.Sentence;
import com.leximemory.backend.models.entities.UserWord;
import com.leximemory.backend.models.entities.Word;
import com.leximemory.backend.models.repositories.AudioRepository;
import com.leximemory.backend.services.exception.sentenceexception.SentenceNotFoundException;
import com.leximemory.backend.util.AudioHandler;
import com.leximemory.backend.util.TextHandler;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * The type Audio service.
 */
@Service
public class AudioService {

  private final AudioRepository audioRepository;

  /**
   * Instantiates a new Audio service.
   *
   * @param audioRepository the audio repository
   */
  @Autowired
  public AudioService(AudioRepository audioRepository) {
    this.audioRepository = audioRepository;
  }

  /**
   * Create word audio.
   *
   * @param word the word
   * @return the audio
   */
  public Audio createWordAudio(Word word) {
    Audio audio = new Audio();
    audio.setWord(word);
    audio.setAudio(AudioHandler.generateAudio(word.getWord()));
    AudioHandler.saveMp3(audio.getAudio());

    return audioRepository.save(audio);
  }

  /**
   * Create audio of word sentence audio.
   *
   * @param word       the word
   * @param sentenceId the sentence id
   * @return the audio
   */
  public Audio createAudioOfWordSentence(Word word, Integer sentenceId) {
    Sentence sentence = word.getExempleSentences().stream()
        .filter(s -> s.getId().equals(sentenceId))
        .findFirst()
        .orElseThrow(SentenceNotFoundException::new);
    List<UserWord> textSentence = sentence.getSentence();
    Audio audio = new Audio();
    audio.setWord(word);
    audio.setAudio(AudioHandler.generateAudio(TextHandler.buildSentence(textSentence)));
    AudioHandler.saveMp3(audio.getAudio());

    return audioRepository.save(audio);
  }
}
