package com.leximemory.backend.models.repositories;

import com.leximemory.backend.models.entities.UserText;
import java.util.List;
import java.util.Optional;
import lombok.NonNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;


/**
 * The interface User text repository.
 */
@Repository
public interface UserTextRepository extends JpaRepository<UserText, Integer> {

  /**
   * Find by user id list.
   *
   * @param userId the user id
   * @return the list
   */
  List<UserText> findByUserId(Integer userId);
}
