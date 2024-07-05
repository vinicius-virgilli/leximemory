package com.leximemory.backend.util;

import com.leximemory.backend.models.entities.UserWord;
import com.leximemory.backend.models.enums.WordType;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 * The type Text handler.
 */
public class TextHandler {

  /**
   * The entry point of application.
   *
   * @param args the input arguments
   */
  public static void main(String[] args) {
    String text = "Hello test.";

    List<List<String>> sentences = TextHandler.splitTextIntoSentences(text);
    for (List<String> sentence : sentences) {
      System.out.println(sentence);
    }
  }

  /**
   * Split text into words list.
   *
   * @param text the text
   * @return the list
   */
  public static List<String> splitTextIntoWords(String text) {
    List<String> words = new ArrayList<>();
    String regex = "[\\p{L}\\p{M}0-9'-]+|[\\p{Punct}]|\\s+";
    Pattern pattern = Pattern.compile(regex);
    Matcher matcher = pattern.matcher(text);

    while (matcher.find()) {
      words.add(matcher.group());
    }

    return words;
  }

  /**
   * Filter valid words list.
   *
   * @param words the words
   * @return the list
   */
  public static List<String> filterValidWords(List<String> words) {
    Set<String> validWordsSet = new LinkedHashSet<>();
    String regex = "^[\\p{Punct}\\s\\d]+$";
    Pattern pattern = Pattern.compile(regex);

    for (String word : words) {
      Matcher matcher = pattern.matcher(word);
      if (!matcher.find() && !Objects.equals(word, " ") && !Objects.equals(word, "")) {
        validWordsSet.add(word);
      }
    }

    return new ArrayList<>(validWordsSet);
  }

  /**
   * Count valid words integer.
   *
   * @param content the content
   * @return the integer
   */
  public static Integer countValidWords(String content) {
    return filterValidWords(splitTextIntoWords(content)).size();
  }

  /**
   * Count new valid words integer.
   *
   * @param userWords the user words
   * @param strings   the strings
   * @return the integer
   */
  public static Integer countNewValidWords(
      List<UserWord> userWords, List<String> strings) {
    int newValidWords = 0;

    List<String> words = new ArrayList<>();
    if (userWords.get(0).getWord() != null) {
      words = userWords.stream()
          .map(w -> w.getWord().getWord().toLowerCase()).toList();
    }

    for (String str : strings) {
      if (!words.contains(str.toLowerCase())) {
        newValidWords += 1;
      }
    }

    return newValidWords;
  }

  /**
   * Split text into sentences list.
   *
   * @param text the text
   * @return the list
   */
  public static List<List<String>> splitTextIntoSentences(String text) {
    List<String> words = splitTextIntoWords(text);
    System.out.println("words = " + words);
    List<List<String>> sentences = new ArrayList<>();
    List<String> currentSentence = new ArrayList<>();

    for (String word : words) {
      currentSentence.add(word);
      if (word.equals(".") || word.equals("!") || word.equals("?") || word.equals("...")) {
        sentences.add(new ArrayList<>(currentSentence));
        currentSentence.clear();
      }
    }

    // Add any remaining words in case the last sentence does not end with a period
    if (!currentSentence.isEmpty()) {
      sentences.add(new ArrayList<>(currentSentence));
    }

    return sentences;
  }

  /**
   * Build sentence string.
   *
   * @param userWords the user words
   * @return the string
   */
  public static String buildSentence(List<UserWord> userWords) {
    return userWords.stream()
        .map(userWord -> userWord.getWord().getWord())
        .collect(Collectors.joining(" "));
  }

  /**
   * Gets word type.
   *
   * @param word the word
   * @return the word type
   */
  public static WordType getWordType(String word) {
    String regex = "^[\\p{Punct}\\s\\d]+$";
    Pattern pattern = Pattern.compile(regex);

    if (pattern.matcher(word).matches()) {
      return WordType.NO_WORD;
    } else {
      return WordType.REAL_WORD;
    }
  }
}
