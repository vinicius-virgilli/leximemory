package com.leximemory.backend;

import com.leximemory.backend.models.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;

/**
 * The type Backend application.
 */
@SpringBootApplication
public class BackendApplication implements CommandLineRunner {

  private final UserRepository userRepository;

  @Autowired
  public BackendApplication(UserRepository userRepository) {
    this.userRepository = userRepository;
  }

  /**
   * The entry point of application.
   *
   * @param args the input arguments
   */


  public static void main(String[] args) {
    SpringApplication.run(BackendApplication.class, args);
  }

  @Override
  public void run(String... args) throws Exception {
    userRepository.findAll().forEach(System.out::println);
  }
}
