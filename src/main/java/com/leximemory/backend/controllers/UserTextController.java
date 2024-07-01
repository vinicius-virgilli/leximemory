package com.leximemory.backend.controllers;

import com.leximemory.backend.controllers.dto.UserTextDto;
import com.leximemory.backend.controllers.dto.UserTextResponseDto;
import com.leximemory.backend.models.entities.UserText;
import com.leximemory.backend.services.QuestionService;
import com.leximemory.backend.services.UserTextService;
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
 * The type User text controller.
 */
@RestController
@RequestMapping("users/{userId}/usertexts")
public class UserTextController {

  private final UserTextService userTextService;

  /**
   * Instantiates a new User text controller.
   *
   * @param userTextService the user text service
   * @param questionService the question service
   */
  @Autowired
  public UserTextController(UserTextService userTextService, QuestionService questionService) {
    this.userTextService = userTextService;
  }

  /**
   * Create text user text dto.
   *
   * @param userId      the user id
   * @param userTextDto the user text dto
   * @return the user text dto
   */
  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  public UserTextResponseDto createText(
      @PathVariable Integer userId,
      @RequestBody UserTextDto userTextDto
  ) {
    UserText newUserText = userTextService.createUserText(userId, userTextDto);
    return UserTextResponseDto.fromEntity(newUserText);
  }

  /**
   * Gets all user texts.
   *
   * @param userId the user id
   * @return the all user texts
   */
  @RequestMapping()
  @ResponseStatus(HttpStatus.CREATED)
  public List<UserTextResponseDto> getAllUserTexts(
      @PathVariable Integer userId
  ) {
    List<UserText> userTexts = userTextService.getAllByUserId(userId);
    return userTexts.stream().map(UserTextResponseDto::fromEntity).toList();
  }

  /**
   * Gets user text by id.
   *
   * @param userId the user id
   * @param textId the text id
   * @return the user text by id
   */
  @GetMapping("/{textId}")
  @ResponseStatus(HttpStatus.OK)
  public UserTextResponseDto getUserTextById(
      @PathVariable Integer userId,
      @PathVariable Integer textId
  ) {
    return UserTextResponseDto.fromEntity(userTextService.getUserTextById(userId, textId));
  }

}
