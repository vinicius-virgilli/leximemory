package com.leximemory.backend.util;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
   * Convert words to text string.
   *
   * @param words the words
   * @return the string
   */
  public static String convertWordsToText(List<String> words) {
    StringBuilder sb = new StringBuilder();

    for (String word : words) {
      sb.append(word);
    }

    return sb.toString();
  }
}
