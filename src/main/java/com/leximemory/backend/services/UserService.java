package com.leximemory.backend.services;

import com.leximemory.backend.controllers.dto.UserCreationDto;
import com.leximemory.backend.exception.UserAlreadyExists;
import com.leximemory.backend.exception.UserNotFoundException;
import com.leximemory.backend.models.entities.User;
import com.leximemory.backend.models.enums.SubjectsInterests;
import com.leximemory.backend.models.repositories.UserRepository;
import com.leximemory.backend.util.Encoder;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * The type User service.
 */
@Service
public class UserService {

  private final UserRepository userRepository;

  /**
   * Instantiates a new User service.
   *
   * @param userRepository the user repository
   */
  @Autowired
  public UserService(UserRepository userRepository) {
    this.userRepository = userRepository;
  }


  /**
   * Create user.
   *
   * @param userCreationDto the new user
   * @return the user
   */
  public User createUser(UserCreationDto userCreationDto) {
    try {
      User user = findUserByEmail(userCreationDto.email());
      throw new UserAlreadyExists();
    } catch (UserNotFoundException e) {
      List<SubjectsInterests> interests =
          Objects.requireNonNullElse(userCreationDto.subjectsInterests(), Collections.emptyList());

      return userRepository.save(User.builder()
          .name(userCreationDto.name())
          .email(userCreationDto.email())
          .password(Encoder.encodePassword(userCreationDto.password()))
          .subjectsInterests(interests)
          .registrationDate(LocalDateTime.now())
          .build());
    }
  }

  /**
   * Find user by email user.
   *
   * @param email the email
   * @return the user
   * @throws UserNotFoundException the user not found exception
   */
  public User findUserByEmail(String email) {
    return userRepository.findByEmail(email)
        .orElseThrow(UserNotFoundException::new);
  }

  /**
   * Gets all users.
   *
   * @return the all users
   */
  public List<User> getAllUsers() {
    return userRepository.findAll();
  }

  /**
   * Gets user by id.
   *
   * @param id the id
   * @return the user by id
   */
  public User getUserById(Integer id) {
    return userRepository.findById(id).orElseThrow(UserNotFoundException::new);
  }
}
