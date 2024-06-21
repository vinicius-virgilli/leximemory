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
   * - cria texto dinamicamente de acordo com assunto, título e usando algumas palavras conhecidas.
   *
   * @return the list
   */
  public static List<String> generateText() {
    // implement logic later
    return Arrays.asList(
        "This", " ", "is", " ", "your", " ", "last", " ", "opportunity", " ", "to", " ",
        "guarantee", " ", "the", " ", "plan", " ", "most", " ", "desired", " ", "by", " ",
        "candidates", " ", "with", " ", "the", " ", "best", " ", "cost-benefit", " ", "to", " ",
        "boost", " ", "your", " ", "preparation", " ", "and", " ", "secure", " ", "your", " ",
        "place", " ", "in", " ", "2024", ".", "\n", "\n", "Enjoy", " ", "and", " ", "guarantee",
        " ", "your", " ", "subscription", "!"
    );
  }

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
    Word newWord = new Word();
    newWord.setWord(word);
    newWord.setMeaning("Meaning of the word");
    newWord.setType(wordType);
    newWord.setExampleSentence(List.of("List with as example sentence"));
    return newWord;
  }

}
