package com.leximemory.backend.services;

import com.leximemory.backend.models.entities.Question;
import com.leximemory.backend.models.entities.Word;
import com.leximemory.backend.models.repositories.QuestionRepository;
import com.leximemory.backend.models.repositories.WordRepository;
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
  private final WordRepository wordRepository;
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
      WordService wordService,
      WordRepository wordRepository

  ) {
    this.questionRepository = questionRepository;
    this.wordService = wordService;
    this.wordRepository = wordRepository;
  }

  /**
   * Add question to word string.
   *
   * @param wordId   the word id
   * @param question the question
   * @return the string
   */
  @Transactional
  public String addQuestionToWord(Integer wordId, Question question) {
    Word word = wordService.getWordById(wordId);
    question.setWordId(word.getId());
    questionRepository.save(question);
    return "Question added to word";
  }

  /**
   * Gets all questions.
   *
   * @return the all questions
   */
  @Transactional
  public List<Question> getAllQuestions() {
    return questionRepository.findAll();
  }

  /**
   * Gets all word questions.
   *
   * @param wordId the word id
   * @return the all word questions
   */
  @Transactional
  public List<Question> getAllWordQuestions(Integer wordId) {
    Word word = wordService.getWordById(wordId);
    return questionRepository.findAllByWordId(wordId);
  }
}
