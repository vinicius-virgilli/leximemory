package com.leximemory.backend.controllers;

import com.leximemory.backend.controllers.dto.ReviewDto;
import com.leximemory.backend.models.entities.Review;
import com.leximemory.backend.services.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

/**
 * The type Review controller.
 */
@RestController
@RequestMapping("/users/{userId}/reviews")
public class ReviewController {

  private final ReviewService reviewService;

  /**
   * Instantiates a new Review controller.
   *
   * @param reviewService the review controller
   */
  @Autowired
  public ReviewController(ReviewService reviewService) {
    this.reviewService = reviewService;
  }

  /**
   * Create review dto.
   *
   * @param userId    the user id
   * @param reviewDto the review dto
   * @return the review dto
   */
  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  public ReviewDto createReview(
      @PathVariable Integer userId,
      @RequestBody ReviewDto reviewDto
  ) {
    Review newReview = reviewService.createReview(userId, reviewDto);
    return ReviewDto.fromEntity(newReview);
  }
}
