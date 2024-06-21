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
    String text = "house's cost-benefit. 2024 ! @ # (*)_ cost-benefit condições imperdíveis";
    List<String> strings = splitTextIntoWords(text);
    System.out.println("text = " + text);
    System.out.println("========================");
    System.out.println("strings = " + strings);
    System.out.println("strings size = " + strings.size());
    System.out.println("========================");
    List<String> validWords = filterValidWords(strings);
    System.out.println("validWords = " + validWords);
    System.out.println("validWords size = " + validWords.size());
  }

  /**
   * Split text into words list.
   *
   * @param text the text
   * @return the list
   */
  public static List<String> splitTextIntoWords(String text) {
    List<String> words = new ArrayList<>();
    String regex = "[\\p{L}\\p{M}0-9'-]+|[\\p{Punct}]";
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
