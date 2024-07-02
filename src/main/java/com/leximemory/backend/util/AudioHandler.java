package com.leximemory.backend.util;

import com.leximemory.backend.models.enums.Speed;
import com.leximemory.backend.services.exception.wordexceptions.WordIoException;
import io.github.cdimascio.dotenv.Dotenv;
import java.io.IOException;
import java.util.Base64;
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

  /**
   * Gets word audio.
   *
   * @param word  the word
   * @param speed the speed
   * @return the word audio
   */
  public static String getWordAudio(String word, Speed speed) {
    PollyClient pollyClient = PollyClient.builder()
        .credentialsProvider(StaticCredentialsProvider.create(
            AwsBasicCredentials.create(AWS_ACCESS_KEY, AWS_SECRET_KEY)
        ))
        .region(REGION)
        .build();

    String ssmlText = "<speak><prosody rate=\"medium\">" + word + "</prosody></speak>";

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

      byte[] audioBytes = IoUtils.toByteArray(synthesizeSpeechResponse);
      return Base64.getEncoder().encodeToString(audioBytes);

    } catch (IOException e) {
      throw new WordIoException();
    }
  }
}
