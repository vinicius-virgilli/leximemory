package com.leximemory.backend.util;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.leximemory.backend.services.exception.wordexceptions.WordIoException;
import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import org.apache.hc.client5.http.classic.methods.HttpPost;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.CloseableHttpResponse;
import org.apache.hc.client5.http.impl.classic.HttpClients;
import org.apache.hc.core5.http.io.entity.StringEntity;

/**
 * The type Word handler.
 */
public class WordHandler {

  private static final String FILE_PATH =
      WordHandler.class.getResource("/words/ranked_words.json").getFile();
  private static final String API_URL = "http://localhost:8080/words";
  private static final int BATCH_SIZE = 100;
  private static final int NUM_THREADS = Runtime.getRuntime().availableProcessors();

  /**
   * The entry point of application.
   *
   * @param args the input arguments
   */
  public static void main(String[] args) {
    ObjectMapper objectMapper = new ObjectMapper();

    Instant startTime = Instant.now();
    try {
      JsonNode rootNode = objectMapper.readTree(new File(FILE_PATH));

      if (rootNode.isArray()) {
        int totalWords = rootNode.size();
        int processedWords = 0;

        ExecutorService executorService = Executors.newFixedThreadPool(NUM_THREADS);
        while (processedWords < totalWords) {
          int batchStart = processedWords;
          int batchEnd = Math.min(processedWords + BATCH_SIZE, totalWords);

          executorService.submit(() -> {
            for (int i = batchStart; i < batchEnd; i++) {
              JsonNode wordNode = rootNode.get(i);
              JsonNode exampleSentencesNode = wordNode.path("exemplo_sentences");

              if (exampleSentencesNode.isArray()) {
                List<String> requestBodies = new ArrayList<>();
                for (JsonNode sentenceNode : exampleSentencesNode) {
                  String requestBody = String.format(
                      "{\"word\": \"%s\", \"rank\": %d, \"repetitions\": %d}",
                      wordNode.path("word").asText(),
                      wordNode.path("rank").asInt(),
                      wordNode.path("repeat").asInt()
                  );
                  requestBodies.add(requestBody);
                }
                sendPostRequests(requestBodies);
              } else {
                System.out.println("Example sentences node is not an array");
              }
            }
          });

          processedWords += BATCH_SIZE;
          double progress = (double) processedWords / totalWords * 100;
          Instant currentTime = Instant.now();
          Duration elapsedTime = Duration.between(startTime, currentTime);
          double estimatedTimeRemainingSeconds =
              ((totalWords - processedWords) * elapsedTime.toMillis()) / (processedWords * 1000.0);
          double estimatedTimeRemainingMinutes = estimatedTimeRemainingSeconds / 60.0;
          int hours = (int) Math.floor(estimatedTimeRemainingMinutes / 60.0);
          int minutes = (int) Math.round(estimatedTimeRemainingMinutes % 60.0);
          System.out.printf("Progress: %.2f%% - Estimated time remaining: %dh%02d min\n", progress,
              hours, minutes);
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

  private static void sendPostRequests(List<String> requestBodies) {
    try {
      try (CloseableHttpClient httpClient = HttpClients.createDefault()) {
        for (String requestBody : requestBodies) {
          HttpPost httpPost = new HttpPost(API_URL);
          httpPost.setEntity(new StringEntity(requestBody, StandardCharsets.UTF_8));
          httpPost.setHeader("Content-Type", "application/json");

          try (CloseableHttpResponse response = httpClient.execute(httpPost)) {
            // do nothing
          }
        }
      }
    } catch (IOException e) {
      throw new WordIoException();
    }
  }
}