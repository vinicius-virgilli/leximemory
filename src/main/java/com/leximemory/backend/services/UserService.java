package com.leximemory.backend.services;

import com.leximemory.backend.controllers.dto.UserCreationDto;
import com.leximemory.backend.models.entities.User;
import com.leximemory.backend.models.repositories.UserRepository;
import com.leximemory.backend.services.exceptions.UserNotFoundException;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {
  private final UserRepository userRepository;
  @Autowired
  public UserService(UserRepository userRepository) {
    this.userRepository = userRepository;
  }

  public User createUser(User newUser) {
    User user = new User();
    user.setName(newUser.getName());
    user.setEmail(newUser.getEmail());
    user.setPassword(newUser.getPassword());
    user.setRegistrationDate(newUser.getRegistrationDate());
    return userRepository.save(user);
  }

  public User findUserByEmail(String email) throws UserNotFoundException {
    Optional<User> user = userRepository.findByEmail(email);
    if (user.isPresent()) {
      return user.get();
    }
    throw new UserNotFoundException();
  }
}
