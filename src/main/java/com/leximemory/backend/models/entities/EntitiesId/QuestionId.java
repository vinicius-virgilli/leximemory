package com.leximemory.backend.models.entities.EntitiesId;

import jakarta.persistence.Embeddable;
import lombok.Data;

/**
 * The type Question id.
 */
@Embeddable
@Data
public class QuestionId {

  private Integer id;
  private Integer wordId;
}
