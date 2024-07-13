package com.leximemory.backend.controllers.dto.worddto;

import com.leximemory.backend.controllers.dto.audio.AudioDto;
import com.leximemory.backend.controllers.dto.sentencedto.WordSentenceDto;
import com.leximemory.backend.controllers.dto.sentencedto.WordSentencesDto;
import com.leximemory.backend.models.entities.Audio;
import com.leximemory.backend.models.entities.Word;
import com.leximemory.backend.models.enums.WordType;
import java.util.ArrayList;
import java.util.List;

/**
 * The type Word complete dto.
 */
public record WordCompleteDto(
    Integer id,
    WordType type,
    Integer wordRank,
    Integer repetitions,
    String word,
    String meaning,
    WordSentencesDto exampleSentences,
    AudioDto audio
) {

  /**
   * To entity word.
   *
   * @return the word
   */
  public Word toEntity() {
    return new Word(
        id,
        type,
        word,
        meaning,
        wordRank,
        repetitions,
        exampleSentences.sentences().stream().map(WordSentenceDto::toEntity).toList(),
        new ArrayList<>(),
        new ArrayList<>(),
        audio.toEntity()
    );
  }

  /**
   * From entity word complete dto.
   *
   * @param word the word
   * @return the word complete dto
   */
  public static WordCompleteDto fromEntity(Word word) {
    Audio audio = word.getAudio();

    if (audio == null) {
      audio = new Audio();
      audio.setWord(word);
      word.setAudio(audio);
    }

    return new WordCompleteDto(
        word.getId(),
        word.getType(),
        word.getWordRank(),
        word.getRepetitions(),
        word.getWord(),
        word.getMeaning(),
        WordSentencesDto.fromEntity(word),
        AudioDto.fromEntity(word.getAudio())
    );
  }
}
