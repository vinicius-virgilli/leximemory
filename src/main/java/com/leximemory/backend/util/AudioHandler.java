package com.leximemory.backend.util;

import com.leximemory.backend.services.exception.wordexceptions.WordIoException;
import io.github.cdimascio.dotenv.Dotenv;
import java.io.FileOutputStream;
import java.io.IOException;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.core.ResponseInputStream;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.polly.PollyClient;
import software.amazon.awssdk.services.polly.model.OutputFormat;
import software.amazon.awssdk.services.polly.model.SynthesizeSpeechRequest;
import software.amazon.awssdk.services.polly.model.SynthesizeSpeechResponse;
import software.amazon.awssdk.utils.IoUtils;

/**
 * The type Audio handler.
 */
public class AudioHandler {

  private static final Dotenv dotenv = Dotenv.load();
  private static final String AWS_ACCESS_KEY = dotenv.get("AWS_ACCESS_KEY");
  private static final String AWS_SECRET_KEY = dotenv.get("AWS_SECRET_KEY");
  private static final Region REGION = Region.US_EAST_1;
  private static final String outputFilePath = "src/main/resources/audios/audio.mp3";

  /**
   * Generate audio byte [ ].
   *
   * @param word the word
   * @return the byte [ ]
   */
  public static byte[] generateAudio(String word) {
    PollyClient pollyClient = PollyClient.builder()
        .credentialsProvider(StaticCredentialsProvider.create(
            AwsBasicCredentials.create(AWS_ACCESS_KEY, AWS_SECRET_KEY)
        ))
        .region(REGION)
        .build();

    String ssmlText = "<speak><prosody rate=\"medium\">" + word
        + "</prosody></speak>";

    SynthesizeSpeechRequest synthesizeSpeechRequest = SynthesizeSpeechRequest.builder()
        .text(ssmlText)
        .textType("ssml")
        .outputFormat(OutputFormat.MP3)
        .engine("neural")
        .voiceId("Ruth")
        .build();

    try {
      ResponseInputStream<SynthesizeSpeechResponse> synthesizeSpeechResponse =
          pollyClient.synthesizeSpeech(synthesizeSpeechRequest);

      return IoUtils.toByteArray(synthesizeSpeechResponse);

    } catch (IOException e) {
      throw new WordIoException();
    }
  }

  /**
   * Save mp 3.
   *
   * @param audioBytes the audio bytes
   */
  public static void saveMp3(byte[] audioBytes) {
    try (FileOutputStream fos = new FileOutputStream(outputFilePath)) {
      fos.write(audioBytes);

    } catch (IOException e) {
      System.err.println(e.getMessage());
    }
  }
}
