package com.leximemory.backend.models.repositories;

import com.leximemory.backend.models.entities.Question;
import com.leximemory.backend.models.entities.Word;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * The interface Word repository.
 */
@Repository
public interface WordRepository extends JpaRepository<Word, Integer> {

  /**
   * Find by word optional.
   *
   * @param word the word
   * @return the optional
   */
  Optional<Word> findByWord(String word);

  /**
   * Find by word ignore case list.
   *
   * @param word the word
   * @return the list
   */
  List<Word> findByWordIgnoreCase(String word);
}
