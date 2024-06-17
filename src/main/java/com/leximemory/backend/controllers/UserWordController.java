package com.leximemory.backend.controllers;

import com.leximemory.backend.controllers.dto.UserWordDto;
import com.leximemory.backend.models.entities.UserWord;
import com.leximemory.backend.models.entities.id.UserWordId;

import com.leximemory.backend.services.UserWordService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

/**
 * The type User word controller.
 */
@RestController
@RequestMapping("/users/{userId}/words")
public class UserWordController {

  private final UserWordService userWordService;

  /**
   * Instantiates a new User word controller.
   *
   * @param userWordService the user word service
   */
  @Autowired
  public UserWordController(UserWordService userWordService) {
    this.userWordService = userWordService;
  }

  /**
   * Create user word user word dto.
   *
   * @param userId      the user id
   * @param wordId      the word id
   * @param userWordDto the user word dto
   * @return the user word dto
   */
  @PostMapping("/{wordId}")
  @ResponseStatus(HttpStatus.CREATED)
  public UserWordDto createUserWord(
      @PathVariable Integer userId,
      @PathVariable Integer wordId,
      @RequestBody UserWordDto userWordDto) {
    UserWordId id = new UserWordId(userId, wordId);
    UserWord newUserWord = userWordService.createUserWord(userWordDto.toEntity(id));
    return UserWordDto.fromEntity(newUserWord);
  }

  /**
   * Gets all user words.
   *
   * @param userId the user id
   * @return the all user words
   */
  @GetMapping
  @ResponseStatus(HttpStatus.OK)
  public List<UserWordDto> getAllUserWords(
      @PathVariable Integer userId
  ) {
    List<UserWord> userWords = userWordService.getAllUserWords(userId);
    return userWords.stream().map(UserWordDto::fromEntity).toList();
  }
}
