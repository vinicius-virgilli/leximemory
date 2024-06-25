package com.leximemory.backend.util;

import com.leximemory.backend.models.entities.Word;
import com.leximemory.backend.models.enums.WordType;
import java.util.Arrays;
import java.util.List;

/**
 * The type Provisional gpt chat api.
 */
public class ProvisionalGptChatApi {

  /**
   * Generate word.
   *
   * @param word     the word
   * @param wordType the word type
   * @return the word
   */
  public static Word generateWord(
      String word,
      WordType wordType
  ) {
    // send request to GPT chat to create fields for the word
    // consider word type PROPER_NAME
    Word newWord = new Word();
    newWord.setWord(word);
    newWord.setMeaning("Meaning of the word");
    newWord.setType(wordType);
    newWord.setExampleSentence(List.of("List with as example sentence"));
    return newWord;
  }

}
