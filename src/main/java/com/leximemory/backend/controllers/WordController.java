package com.leximemory.backend.controllers;

import com.leximemory.backend.controllers.dto.QuestionDto;
import com.leximemory.backend.controllers.dto.WordCreationDto;
import com.leximemory.backend.controllers.dto.WordResponseDto;
import com.leximemory.backend.models.entities.Question;
import com.leximemory.backend.models.entities.Word;
import com.leximemory.backend.services.QuestionService;
import com.leximemory.backend.services.WordService;
import jakarta.transaction.Transactional;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

/**
 * The type Word controller.
 */
@RestController
@RequestMapping("/words")
public class WordController {

  private final WordService wordService;
  private final QuestionService questionService;

  /**
   * Instantiates a new Word controller.
   *
   * @param wordService     the word service
   * @param questionService the question service
   */
  @Autowired
  public WordController(
      WordService wordService,
      QuestionService questionService
  ) {
    this.wordService = wordService;
    this.questionService = questionService;
  }

  /**
   * Create word creation dto.
   *
   * @param wordCreationDto the word creation dto
   * @return the word creation dto
   */
  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  public WordResponseDto createWord(
      @RequestBody WordCreationDto wordCreationDto
  ) {
    Word createdWord = wordService.createWord(wordCreationDto.toEntity());
    return createdWord.toResponseDto();
  }

  /**
   * Gets all words.
   *
   * @return the all words
   */
  @GetMapping
  @ResponseStatus(HttpStatus.OK)
  public List<WordResponseDto> getAllWords() {
    List<Word> words = wordService.getAllWords();
    return words.stream().map(Word::toResponseDto).toList();
  }

  /**
   * Gets word by id.
   *
   * @param id the id
   * @return the word by id
   */
  @GetMapping("/{id}")
  @ResponseStatus(HttpStatus.OK)
  public WordResponseDto getWordById(@PathVariable Integer id) {
    return wordService.getWordById(id).toResponseDto();
  }

  /**
   * Create word question dto.
   *
   * @param wordId              the word id
   * @param questionDto the question creation dto
   * @return the question dto
   */
  @PostMapping("/{wordId}/questions")
  @ResponseStatus(HttpStatus.CREATED)
  public QuestionDto createWordQuestion(
      @PathVariable("wordId") Integer wordId,
      @RequestBody QuestionDto questionDto
  ) {
    Question newQuestion = questionService.createQuestion(
        wordId,
        questionDto.toEntity()
    );
    return QuestionDto.fromEntity(newQuestion);
  }

  /**
   * Gets all word questions.
   *
   * @param wordId the word id
   * @return the all word questions
   */
  @Transactional
  @GetMapping("/{wordId}/questions")
  @ResponseStatus(HttpStatus.OK)
  public List<QuestionDto> getAllWordQuestions(
      @PathVariable Integer wordId
  ) {
    Word word = wordService.getWordById(wordId);
    List<Question> questions = word.getQuestions();
    return questions.stream().map(QuestionDto::fromEntity).toList();
  }
}
