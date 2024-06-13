package com.leximemory.backend.services;

import com.leximemory.backend.controllers.dto.UserCreationDto;
import com.leximemory.backend.models.entities.User;
import com.leximemory.backend.models.repositories.UserRepository;
import com.leximemory.backend.exception.UserAlreadyExists;
import com.leximemory.backend.exception.UserNotFoundException;
import com.leximemory.backend.util.Date;
import com.leximemory.backend.util.Encoder;
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
    Optional<User> user = userRepository.findByEmail(userCreationDto.email());
    if (user.isPresent()) {
      throw new UserAlreadyExists();
    }
    return userRepository.save(User.builder()
        .name(userCreationDto.name())
        .email(userCreationDto.email())
        .password(Encoder.encodePassword(userCreationDto.password()))
        .registrationDate(Date.currentDate())
        .build());
  }

  /**
   * Find user by email user.
   *
   * @param email the email
   * @return the user
   * @throws UserNotFoundException the user not found exception
   */
  public User findUserByEmail(String email) {
    Optional<User> user = userRepository.findByEmail(email);
    if (user.isPresent()) {
      return user.get();
    }
    throw new UserNotFoundException();
  }
}
