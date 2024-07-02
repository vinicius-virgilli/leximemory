package com.leximemory.backend.services;

import com.leximemory.backend.models.entities.Audio;
import com.leximemory.backend.models.entities.Word;
import com.leximemory.backend.models.enums.Speed;
import com.leximemory.backend.models.repositories.AudioRepository;
import com.leximemory.backend.util.AudioHandler;
import java.io.IOException;
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
    audio.setSlowAudio(AudioHandler.getWordAudio(word.getWord(), Speed.SLOW));
    audio.setMediumAudio(AudioHandler.getWordAudio(word.getWord(), Speed.MEDIUM));
    return audioRepository.save(audio);
  }
}