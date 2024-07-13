package com.leximemory.backend.controllers.dto.audio;

import com.leximemory.backend.models.entities.Audio;

/**
 * The type Audio dto.
 */
public record AudioDto(
    Integer id,
    byte[] audio
) {

  /**
   * To entity audio.
   *
   * @return the audio
   */
  public Audio toEntity() {
    return new Audio(
        id,
        audio,
        null,
        null,
        null
    );
  }

  /**
   * From entity audio dto.
   *
   * @param newAudio the new audio
   * @return the audio dto
   */
  public static AudioDto fromEntity(Audio newAudio) {
    return new AudioDto(newAudio.getId(), newAudio.getAudio());
  }
}
