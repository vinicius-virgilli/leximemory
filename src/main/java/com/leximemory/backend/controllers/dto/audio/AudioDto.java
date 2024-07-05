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
   * From entity audio dto.
   *
   * @param newAudio the new audio
   * @return the audio dto
   */
  public static AudioDto fromEntity(Audio newAudio) {
    return new AudioDto(newAudio.getId(), newAudio.getAudio());
  }
}
