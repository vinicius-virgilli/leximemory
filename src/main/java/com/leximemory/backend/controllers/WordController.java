package com.leximemory.backend.controllers;

import com.leximemory.backend.controllers.dto.questiondto.QuestionDto;
import com.leximemory.backend.controllers.dto.worddto.WordWithSentencesDto;
import com.leximemory.backend.controllers.dto.worddto.WordCreationDto;
import com.leximemory.backend.controllers.dto.worddto.WordDto;
import com.leximemory.backend.controllers.dto.worddto.WordResponseDto;
import com.leximemory.backend.models.entities.Question;
import com.leximemory.backend.models.entities.Word;
import com.leximemory.backend.services.QuestionService;
import com.leximemory.backend.services.WordService;
import jakarta.transaction.Transactional;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;


/**
 * The type Word controller.
 */
@RestController
@RequestMapping
public class WordController {

  private final WordService wordService;
  private final QuestionService questionService;
  private static final Logger logger = LoggerFactory.getLogger(WordController.class);


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
   * Create word response dto.
   *
   * @param wordCreationDto the word creation dto
   * @return the word response dto
   */
  @PostMapping("/words")
  @ResponseStatus(HttpStatus.CREATED)
  public WordResponseDto createWord(@RequestBody WordCreationDto wordCreationDto) {

    logger.info("Received POST request to create a word. Body: " + wordCreationDto);

    Word newWord = wordService.createWord(wordCreationDto);

    System.out.println("Created word successfully. Word: " + newWord);

    return WordResponseDto.fromEntity(newWord);
  }

  /**
   * Gets all words.
   *
   * @return the all words
   */
  @GetMapping("/words")
  @ResponseStatus(HttpStatus.OK)
  public List<WordDto> getAllWords() {
    List<Word> words = wordService.getAllWords();
    return words.stream().map(WordDto::fromEntity).toList();
  }

  /**
   * Gets word by id.
   *
   * @param id the id
   * @return the word by id
   */
  @GetMapping("/words/{id}")
  @ResponseStatus(HttpStatus.OK)
  public WordDto getWordById(@PathVariable Integer id) {
    return WordDto.fromEntity(wordService.getWordById(id));
  }

  /**
   * Create word question dto.
   *
   * @param wordId      the word id
   * @param questionDto the question creation dto
   * @return the question dto
   */
  @PostMapping("/words/{wordId}/questions")
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
  @GetMapping("/words/{wordId}/questions")
  @ResponseStatus(HttpStatus.OK)
  public List<QuestionDto> getAllWordQuestions(
      @PathVariable Integer wordId
  ) {
    Word word = wordService.getWordById(wordId);
    List<Question> questions = word.getQuestions();
    return questions.stream().map(QuestionDto::fromEntity).toList();
  }

  /**
   * Search word dto.
   *
   * @param string the string
   * @return the word dto
   */
  @GetMapping("/words/search")
  @ResponseStatus(HttpStatus.OK)
  public WordDto searchWord(
      @RequestParam("word") String string
  ) {
    Word word = wordService.getWordByWord(string);
    return WordDto.fromEntity(word);
  }

  /**
   * Create example sentences word response dto.
   *
   * @param userId          the user id
   * @param wordId          the word id
   * @param wordCreationDto the word creation dto
   * @return the word response dto
   */
  @PutMapping("users/{userId}/words/{wordId}/sentences")
  @ResponseStatus(HttpStatus.OK)
  public WordWithSentencesDto createExampleSentences(
      @PathVariable("userId") Integer userId,
      @PathVariable("wordId") Integer wordId,
      @RequestBody WordCreationDto wordCreationDto
  ) {
    Word word = wordService.createWordExampleSentences(userId, wordId, wordCreationDto);
    return WordWithSentencesDto.fromEntity(word);
  }
}