package com.leximemory.backend.controllers;

import com.leximemory.backend.controllers.dto.UserCreationDto;
import com.leximemory.backend.controllers.dto.UserDto;
import com.leximemory.backend.models.entities.User;
import com.leximemory.backend.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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

  /**
   * Instantiates a new User controller.
   *
   * @param userService the user service
   */
  @Autowired
  public UserController(UserService userService) {
    this.userService = userService;
  }

  /**
   * Create user response entity.
   *
   * @param userCreationDto the user creation dto
   * @return the response entity
   */
  @PostMapping()
  @ResponseStatus(HttpStatus.CREATED)
  public UserDto createUser(@RequestBody UserCreationDto userCreationDto) {
    User newUser = userService.createUser(userCreationDto);
    return newUser.toDto();
  }
}
