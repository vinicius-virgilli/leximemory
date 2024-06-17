package com.leximemory.backend.controllers;

import com.leximemory.backend.controllers.dto.UserCreationDto;
import com.leximemory.backend.controllers.dto.UserResponseDto;
import com.leximemory.backend.models.entities.User;
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
  public UserResponseDto createUser(@RequestBody UserCreationDto userCreationDto) {
    User newUser = userService.createUser(userCreationDto);
    return newUser.toResponseDto();
  }

  /**
   * Gets all users.
   *
   * @return the all users
   */
  @GetMapping
  @ResponseStatus(HttpStatus.OK)
  public List<User> getAllUsers() {
    return userService.getAllUsers();
  }

  /**
   * Gets user by id.
   *
   * @param id the id
   * @return the user by id
   */
  @GetMapping("/{id}")
  public User getUserById(@PathVariable Integer id) {
    return userService.getUserById(id);
  }
}
