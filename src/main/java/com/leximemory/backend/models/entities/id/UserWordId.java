package com.leximemory.backend.models.entities.id;

import jakarta.persistence.Embeddable;
import lombok.Data;

/**
 * The type User word id.
 */
@Embeddable
@Data
public class UserWordId {

  private Integer userId;
  private Integer wordId;
}
