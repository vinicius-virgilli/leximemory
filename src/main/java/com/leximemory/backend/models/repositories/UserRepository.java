package com.leximemory.backend.models.repositories;

import com.leximemory.backend.models.entities.User;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * The interface User repository.
 */
@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

  /**
   * Find by email optional.
   *
   * @param email the email
   * @return the optional
   */
  Optional<User> findByEmail(String email);
}
