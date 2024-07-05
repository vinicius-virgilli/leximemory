package com.leximemory.backend.util;

import com.leximemory.backend.models.entities.Word;
import com.leximemory.backend.models.enums.WordType;
import java.util.ArrayList;
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
    // newWord.setExampleSentence(List.of("List with as example sentence"));
    return newWord;
  }

  /**
   * Gets word text sentences.
   *
   * @param word the word
   * @return the word text sentences
   */
  public static List<String> getWordTextSentences(String word) {
    List<String> sentences = new ArrayList<>();
    sentences.add("Sentence number one for " + word);
    sentences.add("Sentence number two for " + word);
    sentences.add("Sentence number three for " + word);
    sentences.add("Sentence number four for " + word);
    sentences.add("Sentence number five for " + word);
    return sentences;
  }

  /**
   * Gets sentences translations.
   *
   * @param textSentences the text sentences
   * @return the sentences translations
   */
  public static List<String> getSentencesTranslations(List<String> textSentences) {
    List<String> translations = new ArrayList<>();
    translations.add("Translation number one");
    translations.add("Translation number two");
    translations.add("Translation number three");
    translations.add("Translation number four");
    translations.add("Translation number five");
    return translations;
  }

}
