package com.leximemory.backend.util;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.leximemory.backend.services.exception.wordexceptions.WordIoException;
import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import org.apache.hc.client5.http.classic.methods.HttpGet;
import org.apache.hc.client5.http.classic.methods.HttpPut;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.CloseableHttpResponse;
import org.apache.hc.client5.http.impl.classic.HttpClients;
import org.apache.hc.core5.http.io.entity.StringEntity;

/**
 * The type Word example handler.
 */
public class WordExampleHandler {

  private static final String FILE_PATH =
      WordExampleHandler.class.getResource("/words/ranked_words.json").getFile();
  private static final String SEARCH_API_URL = "http://localhost:8080/words/search?word=";
  private static final String UPDATE_API_URL = "http://localhost:8080/users/1/words/%d/sentences";
  private static final int NUM_THREADS = Runtime.getRuntime().availableProcessors();

  /**
   * The entry point of application.
   *
   * @param args the input arguments
   */
  public static void main(String[] args) {
    ObjectMapper objectMapper = new ObjectMapper();

    try {
      JsonNode rootNode = objectMapper.readTree(new File(FILE_PATH));

      if (rootNode.isArray()) {
        ExecutorService executorService = Executors.newFixedThreadPool(NUM_THREADS);

        for (JsonNode wordNode : rootNode) {
          executorService.submit(() -> processWordNode(wordNode));
        }

        executorService.shutdown();
        executorService.awaitTermination(Long.MAX_VALUE, TimeUnit.NANOSECONDS);
      } else {
        System.out.println("Root node is not an array");
      }
    } catch (IOException | InterruptedException e) {
      throw new WordIoException();
    }
  }

  private static void processWordNode(JsonNode wordNode) {
    String word = wordNode.path("word").asText();

    JsonNode wordDetails = getWordDetails(word);
    if (wordDetails != null && wordDetails.has("id")) {
      int wordId = wordDetails.path("id").asInt();

      JsonNode exampleSentencesNode = wordDetails.path("exampleSentences").path("sentences");
      if (exampleSentencesNode.isArray() && exampleSentencesNode.size() == 0) {
        List<JsonNode> exampleSentences = new ArrayList<>();
        if (wordNode.has("exemplo_sentences")) {
          for (JsonNode sentenceNode : wordNode.get("exemplo_sentences")) {
            exampleSentences.add(sentenceNode);
          }
        }
        updateWordSentences(wordId, exampleSentences);
      }
    }
  }

  private static JsonNode getWordDetails(String word) {
    try (CloseableHttpClient httpClient = HttpClients.createDefault()) {
      HttpGet httpGet = new HttpGet(SEARCH_API_URL + word);
      httpGet.setHeader("Content-Type", "application/json");

      try (CloseableHttpResponse response = httpClient.execute(httpGet)) {
        int statusCode = response.getCode();
        if (statusCode == 200) {
          ObjectMapper objectMapper = new ObjectMapper();
          return objectMapper.readTree(response.getEntity().getContent());
        }
      }
    } catch (IOException e) {
      throw new WordIoException();
    }
    return null;
  }

  private static void updateWordSentences(int wordId, List<JsonNode> exampleSentences) {
    try (CloseableHttpClient httpClient = HttpClients.createDefault()) {
      String url = String.format(UPDATE_API_URL, wordId);
      HttpPut httpPut = new HttpPut(url);
      String requestBody = buildUpdateRequestBody(exampleSentences);
      // System.out.println(requestBody);
      httpPut.setEntity(new StringEntity(requestBody, StandardCharsets.UTF_8));
      httpPut.setHeader("Content-Type", "application/json");

      try (CloseableHttpResponse response = httpClient.execute(httpPut)) {
        int statusCode = response.getCode();
        System.out.printf("Update status: %d | wordId: %d\n", statusCode, wordId);
      }
    } catch (IOException e) {
      throw new WordIoException();
    }
  }

  private static String buildUpdateRequestBody(List<JsonNode> exampleSentences) {
    StringBuilder sentencesJson = new StringBuilder();
    sentencesJson.append("{ \"sentences\": [");

    for (int i = 0; i < exampleSentences.size(); i++) {
      JsonNode sentenceNode = exampleSentences.get(i);
      String textSentence = escapeSpecialCharacters(sentenceNode.path("phase").asText());
      int tatoebaAudioId = sentenceNode.path("audio_id").asInt();
      String translation = escapeSpecialCharacters(sentenceNode.path("translation").asText());

      sentencesJson.append(String.format(
          "{\"textSentence\": \"%s\", \"tatoebaAudioId\": %d, \"translation\": \"%s\"}",
          textSentence, tatoebaAudioId, translation));

      // Adiciona vírgula se não for o último elemento
      if (i < exampleSentences.size() - 1) {
        sentencesJson.append(",");
      }
    }
    sentencesJson.append("]}");

    return sentencesJson.toString();
  }

  private static String escapeSpecialCharacters(String text) {
    // Escape characters like backslash (\) and double quotes (") inside the text
    return text.replace("\\", "\\\\").replace("\"", "\\\"");
  }

}
