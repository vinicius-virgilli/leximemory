package com.leximemory.backend.models.repositories;

import com.leximemory.backend.models.entities.UserWord;
import com.leximemory.backend.models.entities.Word;
import com.leximemory.backend.models.entities.id.UserWordId;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * The interface Word repository.
 */
@Repository
public interface UserWordRepository extends JpaRepository<UserWord, UserWordId> {

  /**
   * Find by user id list.
   *
   * @param userId the user id
   * @return the list
   */
  List<UserWord> findByUserId(Integer userId);
}
