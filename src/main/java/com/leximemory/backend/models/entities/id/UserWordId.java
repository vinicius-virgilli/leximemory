package com.leximemory.backend.models.entities.id;

import com.leximemory.backend.models.entities.UserWord;
import jakarta.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * The type User word id.
 */
@Embeddable
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserWordId implements Serializable {

  private Integer userId;
  private Integer wordId;

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    UserWordId that = (UserWordId) o;
    return Objects.equals(userId, that.userId) && Objects.equals(wordId, that.wordId);
  }

  @Override
  public int hashCode() {
    return Objects.hash(userId, wordId);
  }
}
