package com.leximemory.backend.models.repositories;

import com.leximemory.backend.models.entities.User;
import com.leximemory.backend.models.entities.UserWord;
import com.leximemory.backend.models.entities.Word;
import com.leximemory.backend.models.entities.id.UserWordId;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
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

  /**
   * Find by user and word optional.
   *
   * @param user the user
   * @param word the word
   * @return the optional
   */
  @Query("SELECT uw FROM UserWord uw WHERE uw.user = :user AND uw.word.word = :word")
  List<UserWord> findByUserAndWordWord(@Param("user") User user, @Param("word") String word);
}
