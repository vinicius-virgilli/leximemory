package com.leximemory.backend.controllers.dto.reviewdto;

import com.leximemory.backend.models.entities.FlashCard;
import com.leximemory.backend.models.entities.Question;
import com.leximemory.backend.models.entities.Review;
import com.leximemory.backend.models.enums.ReviewType;
import java.time.LocalDate;
import java.util.List;

/**
 * The type Review dto.
 */
public record ReviewDto(
    Integer id,
    String title,
    Integer userId,
    List<Integer> questionsId,
    List<Integer> flashCardsId,
    Integer textId,
    List<ReviewType> reviewTypes,
    LocalDate registrationDate,
    LocalDate proposedDate,
    LocalDate executionDate
) {

  /**
   * From entity review dto.
   *
   * @param review the review
   * @return the review dto
   */
  public static ReviewDto fromEntity(Review review) {
    return new ReviewDto(
        review.getId(),
        review.getTitle(),
        review.getUser().getId(),
        review.getQuestions().stream().map(Question::getId).toList(),
        review.getFlashcards().stream().map(FlashCard::getId).toList(),
        review.getUserText().getId(),
        review.getReviewTypes(),
        review.getRegistrationDate(),
        review.getProposedDate(),
        review.getExecutionDate()
    );
  }
}
