package com.leximemory.backend.models.repositories;

import com.leximemory.backend.models.entities.Question;
import com.leximemory.backend.models.entities.Word;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * The interface User repository.
 */
@Repository
public interface QuestionRepository extends JpaRepository<Question, Integer> {
  public List<Question> findAllByWordId(Integer wordId);
}
