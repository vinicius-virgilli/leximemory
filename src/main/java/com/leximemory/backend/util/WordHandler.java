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
  private static final int NUM_THREADS = Runtime.getRuntime().availableProcessors();
  private static final int START_INDEX = 0; // Starting index for processing

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
        ExecutorService executorService = Executors.newFixedThreadPool(NUM_THREADS);

        for (int i = START_INDEX; i < totalWords; i++) {
          final int index = i; // Final variable for lambda expression

          executorService.submit(() -> {
            JsonNode wordNode = rootNode.get(index);
            String word = wordNode.path("word").asText();
            int rank = wordNode.path("rank").asInt();
            int repetitions = wordNode.path("repeat").asInt();

            sendPostRequest(word, rank, repetitions);

            double progress = (double) (index - START_INDEX + 1) / (totalWords - START_INDEX) * 100;
            Instant currentTime = Instant.now();
            Duration elapsedTime = Duration.between(startTime, currentTime);
            double estimatedTimeRemainingSeconds =
                ((totalWords - index) * elapsedTime.toMillis()) / ((index - START_INDEX + 1)
                    * 1000.0);
            double estimatedTimeRemainingMinutes = estimatedTimeRemainingSeconds / 60.0;
            int hours = (int) Math.floor(estimatedTimeRemainingMinutes / 60.0);
            int minutes = (int) Math.round(estimatedTimeRemainingMinutes % 60.0);
            System.out.printf("Progress: %.2f%% - Estimated time remaining: %dh%02d min\n",
                progress,
                hours, minutes);
          });
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

  private static void sendPostRequest(String word, int rank, int repetitions) {

    try (CloseableHttpClient httpClient = HttpClients.createDefault()) {
      HttpPost httpPost = new HttpPost(API_URL);
      String requestBody = buildRequestBody(word, rank, repetitions);
      httpPost.setEntity(new StringEntity(requestBody, StandardCharsets.UTF_8));
      httpPost.setHeader("Content-Type", "application/json");

      try (CloseableHttpResponse response = httpClient.execute(httpPost)) {
        int statusCode = response.getCode();
        System.out.printf("Response status: %d | word: \"%s\" | ranking: %d\n", statusCode, word,
            rank);
        // Optionally, handle response content or log here
      }
    } catch (IOException e) {
      throw new WordIoException();
    }
  }

  private static String buildRequestBody(String word, int rank, int repetitions) {

    return String.format(
        "{\"word\": \"%s\", \"rank\": %d, \"repetitions\": %d}",
        word, rank, repetitions
    );
  }
}
