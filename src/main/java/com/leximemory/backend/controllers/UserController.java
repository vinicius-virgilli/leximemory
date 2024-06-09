package com.leximemory.backend.controllers;

import com.leximemory.backend.controllers.dto.ResponseDto;
import com.leximemory.backend.controllers.dto.UserCreationDto;
import com.leximemory.backend.controllers.dto.UserDto;
import com.leximemory.backend.models.entities.User;
import com.leximemory.backend.services.UserService;
import com.leximemory.backend.services.exceptions.UserNotFoundException;
import com.leximemory.backend.util.Encoder;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
public class UserController {
  private final UserService userService;
  @Autowired
  public UserController(UserService userService) {
    this.userService = userService;
  }

  @PostMapping()
  public ResponseEntity<ResponseDto<UserDto>> createUser(@RequestBody UserCreationDto userCreationDto) {
    try {
      User user = userService.findUserByEmail(userCreationDto.email());
      ResponseDto<UserDto> responseDto = new ResponseDto<UserDto>("User already exists", null);
      return ResponseEntity.status(HttpStatus.CONFLICT).body(responseDto);
    } catch (UserNotFoundException e) {
      User newUser = userService.createUser(
          new User(
              userCreationDto.name(),
              userCreationDto.email(),
              Encoder.encodePassword(userCreationDto.password()),
              Timestamp.valueOf(LocalDateTime.now())
          ));
      ResponseDto<UserDto> responseDto = new ResponseDto<UserDto>("User created successfully",
          new UserDto(newUser.getId(), newUser.getName(), newUser.getEmail()));
      return ResponseEntity.status(HttpStatus.CREATED).body(responseDto);
    }
  }
}
