package com.leximemory.backend.controllers;

import com.leximemory.backend.controllers.dto.SentenceDto;
import com.leximemory.backend.models.entities.Sentence;
import com.leximemory.backend.services.SentenceService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

/**
 * The type Sentence controller.
 */
@RestController
@RequestMapping("/users/{userId}/usertexts/{userTextId}/sentences")
public class SentenceController {

  private final SentenceService sentenceService;

  /**
   * Instantiates a new Sentence controller.
   *
   * @param sentenceService the sentence service
   */
  @Autowired
  public SentenceController(SentenceService sentenceService) {
    this.sentenceService = sentenceService;
  }

  /**
   * Gets all sentences by user text id.
   *
   * @param userId     the user id
   * @param userTextId the user text id
   * @return the all sentences by user text id
   */
  @GetMapping
  @ResponseStatus(HttpStatus.OK)
  public List<SentenceDto> getAllSentencesByUserTextId(
      @PathVariable("userId") Integer userId,
      @PathVariable("userTextId") Integer userTextId
  ) {
    List<Sentence> sentences = sentenceService.getAllByUserTextId(userTextId);
    return sentences.stream()
        .map(SentenceDto::fromEntity)
        .toList();
  }
}
