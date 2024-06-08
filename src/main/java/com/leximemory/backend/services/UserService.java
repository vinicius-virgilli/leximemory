package com.leximemory.backend.services;

import com.leximemory.backend.controllers.dto.UserCreationDto;
import com.leximemory.backend.models.entities.User;
import com.leximemory.backend.models.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {
  private final UserRepository userRepository;
  @Autowired
  public UserService(UserRepository userRepository) {
    this.userRepository = userRepository;
  }

  public User createUser(UserCreationDto userCreationDto) {
    User user = new User();
    user.setName(userCreationDto.name());
    user.setEmail(userCreationDto.email());
    user.setPassword(userCreationDto.password());
    user.setRegistrationDate(userCreationDto.registrationDate());
    return userRepository.save(user);
  }
}
