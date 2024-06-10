package com.leximemory.backend.services;

import com.leximemory.backend.models.entities.User;
import com.leximemory.backend.models.repositories.UserRepository;
import com.leximemory.backend.services.exceptions.UserNotFoundException;
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
   * @param newUser the new user
   * @return the user
   */
  public User createUser(User newUser) {
    return userRepository.save(newUser);
  }

  /**
   * Find user by email user.
   *
   * @param email the email
   * @return the user
   * @throws UserNotFoundException the user not found exception
   */
  public User findUserByEmail(String email) throws UserNotFoundException {
    Optional<User> user = userRepository.findByEmail(email);
    if (user.isPresent()) {
      return user.get();
    }
    throw new UserNotFoundException();
  }
}
