package com.leximemory.backend.services;

import com.leximemory.backend.models.entities.Question;
import com.leximemory.backend.models.entities.Word;
import com.leximemory.backend.models.repositories.QuestionRepository;
import jakarta.transaction.Transactional;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * The type Question service.
 */
@Service
public class QuestionService {

  private final WordService wordService;
  private final QuestionRepository questionRepository;

  /**
   * Instantiates a new Question service.
   *
   * @param questionRepository the question repository
   * @param wordService        the word service
   */
  @Autowired
  public QuestionService(
      QuestionRepository questionRepository,
      WordService wordService

  ) {
    this.questionRepository = questionRepository;
    this.wordService = wordService;
  }

  /**
   * Add question to word string.
   *
   * @param wordId   the word id
   * @param question the question
   * @return the string
   */
  @Transactional
  public Question createQuestion(Integer wordId, Question question) {
    Word word = wordService.getWordById(wordId);
    question.setWord(word);

    return questionRepository.save(question);
  }

  /**
   * Gets all questions.
   *
   * @return the all questions
   */
  public List<Question> getAllQuestions() {
    return questionRepository.findAll();
  }
}
