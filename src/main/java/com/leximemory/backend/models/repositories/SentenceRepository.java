package com.leximemory.backend.models.repositories;

import com.leximemory.backend.models.entities.Sentence;
import com.leximemory.backend.models.entities.User;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * The interface Sentence repository.
 */
@Repository
public interface SentenceRepository extends JpaRepository<Sentence, Integer> {

  /**
   * Find by user text id list.
   *
   * @param userTextId the user text id
   * @return the list
   */
  List<Sentence> findByUserTextId(Integer userTextId);

}
