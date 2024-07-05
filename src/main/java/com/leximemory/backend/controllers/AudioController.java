package com.leximemory.backend.controllers;

import com.leximemory.backend.controllers.dto.audio.AudioDto;
import com.leximemory.backend.models.entities.Word;
import com.leximemory.backend.services.AudioService;
import com.leximemory.backend.services.WordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

/**
 * The type Audio controller.
 */
@RestController
@RequestMapping("users/{userId}")
public class AudioController {

  private final AudioService audioService;
  private final WordService wordService;

  /**
   * Instantiates a new Audio controller.
   *
   * @param audioService the audio service
   * @param wordService  the word service
   */
  @Autowired
  public AudioController(AudioService audioService, WordService wordService) {
    this.audioService = audioService;
    this.wordService = wordService;
  }

  /**
   * Create word audio.
   *
   * @param wordId the word id
   * @return the audio dto
   */
  @RequestMapping("/words/{wordId}/audio")
  @ResponseStatus(HttpStatus.CREATED)
  public AudioDto createWordAudio(@PathVariable Integer wordId) {
    Word word = wordService.getWordById(wordId);
    return AudioDto.fromEntity(audioService.createWordAudio(word));
  }

  /**
   * Create audio of word sentence audio dto.
   *
   * @param wordId     the word id
   * @param sentenceId the sentence id
   * @return the audio dto
   */
  @RequestMapping("/words/{wordId}/sentences/{sentenceId}/audio")
  @ResponseStatus(HttpStatus.CREATED)
  public AudioDto createAudioOfWordSentence(
      @PathVariable("wordId") Integer wordId,
      @PathVariable("sentenceId") Integer sentenceId
  ) {
    Word word = wordService.getWordById(wordId);
    return AudioDto.fromEntity(audioService.createAudioOfWordSentence(word, sentenceId));
  }
}
