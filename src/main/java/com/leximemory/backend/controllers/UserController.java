package com.leximemory.backend.controllers;

import com.leximemory.backend.controllers.dto.FlashCardDto;
import com.leximemory.backend.controllers.dto.UserDto;
import com.leximemory.backend.models.entities.FlashCard;
import com.leximemory.backend.models.entities.User;
import com.leximemory.backend.services.FlashCardService;
import com.leximemory.backend.services.UserService;
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
 * The type User controller.
 */
@RestController
@RequestMapping("/users")
public class UserController {

  private final UserService userService;
  private final FlashCardService flashCardService;


  /**
   * Instantiates a new User controller.
   *
   * @param userService      the user service
   * @param flashCardService the flash card service
   */
  @Autowired
  public UserController(
      UserService userService,
      FlashCardService flashCardService
  ) {
    this.userService = userService;
    this.flashCardService = flashCardService;
  }

  /**
   * Create user response entity.
   *
   * @param userDto the user creation dto
   * @return the response entity
   */
  @PostMapping()
  @ResponseStatus(HttpStatus.CREATED)
  public UserDto createUser(@RequestBody UserDto userDto) {
    User newUser = userService.createUser(userDto.toEntity());
    return UserDto.fromEntity(newUser);
  }

  /**
   * Gets all users.
   *
   * @return the all users
   */
  @GetMapping
  @ResponseStatus(HttpStatus.OK)
  public List<UserDto> getAllUsers() {
    return userService.getAllUsers().stream().map(UserDto::fromEntity).toList();
  }

  /**
   * Gets user by id.
   *
   * @param id the id
   * @return the user by id
   */
  @GetMapping("/{id}")
  public UserDto getUserById(@PathVariable Integer id) {
    return UserDto.fromEntity(userService.getUserById(id));
  }

  /**
   * Gets user flashcards.
   *
   * @param userId the user id
   * @return the user flashcards
   */
  @GetMapping("/{userId}/flashcards")
  @ResponseStatus(HttpStatus.OK)
  public List<FlashCardDto> getUserFlashcards(
      @PathVariable Integer userId
  ) {
    List<FlashCard> flashCards = flashCardService.getAllByUserId(userId);
    return flashCards.stream().map(FlashCardDto::fromEntity).toList();
  }
}
