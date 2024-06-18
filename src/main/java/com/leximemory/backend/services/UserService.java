package com.leximemory.backend.services;

import com.leximemory.backend.exception.UserAlreadyExistsException;
import com.leximemory.backend.exception.UserNotFoundException;
import com.leximemory.backend.models.entities.User;
import com.leximemory.backend.models.enums.SubjectsInterests;
import com.leximemory.backend.models.repositories.UserRepository;
import com.leximemory.backend.util.Encoder;
import java.time.LocalDateTime;
import java.util.List;
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
   * @param newUser the new user
   * @return the user
   */
  public User createUser(User newUser) {
    try {
      User user = findUserByEmail(newUser.getEmail());
      throw new UserAlreadyExistsException();
    } catch (UserNotFoundException e) {
      List<SubjectsInterests> interests = newUser.getSubjectsInterests();

      return userRepository.save(User.builder()
          .name(newUser.getName())
          .email(newUser.getEmail())
          .password(Encoder.encodePassword(newUser.getPassword()))
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
