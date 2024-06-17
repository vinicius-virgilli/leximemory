package com.leximemory.backend.controllers;

import com.leximemory.backend.controllers.dto.WordCreationDto;
import com.leximemory.backend.controllers.dto.WordResponseDto;
import com.leximemory.backend.models.entities.Word;
import com.leximemory.backend.services.WordService;
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

  /**
   * Instantiates a new Word controller.
   *
   * @param wordService the word service
   */
  @Autowired
  public WordController(WordService wordService) {
    this.wordService = wordService;
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
}
