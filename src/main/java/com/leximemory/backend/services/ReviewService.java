package com.leximemory.backend.services;

import com.leximemory.backend.controllers.dto.ReviewDto;
import com.leximemory.backend.models.entities.FlashCard;
import com.leximemory.backend.models.entities.Question;
import com.leximemory.backend.models.entities.Review;
import com.leximemory.backend.models.entities.User;
import com.leximemory.backend.models.entities.UserText;
import com.leximemory.backend.models.enums.ReviewType;
import com.leximemory.backend.models.repositories.ReviewRepository;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * The type Review service.
 */
@Service
public class ReviewService {

  private final ReviewRepository reviewRepository;
  private final UserService userService;
  private final FlashCardService flashCardService;
  private final QuestionService questionService;
  private final UserTextService userTextService;

  /**
   * Instantiates a new Review service.
   *
   * @param reviewRepository the review repository
   * @param userService      the user service
   * @param flashCardService the flash card service
   * @param questionService  the question service
   * @param userTextService  the user text service
   */
  @Autowired
  public ReviewService(ReviewRepository reviewRepository, UserService userService,
      FlashCardService flashCardService, QuestionService questionService,
      UserTextService userTextService) {
    this.reviewRepository = reviewRepository;
    this.userService = userService;
    this.flashCardService = flashCardService;
    this.questionService = questionService;
    this.userTextService = userTextService;
  }

  /**
   * Create review.
   *
   * @param userId    the user id
   * @param reviewDto the review dto
   * @return the review
   */
  public Review createReview(Integer userId, ReviewDto reviewDto) {
    User user = userService.getUserById(userId);
    Review newReview = new Review();
    newReview.setUser(user);

    List<ReviewType> reviewTypes = new ArrayList<>();

    if (reviewDto.questionsId() != null) {
      List<Question> questions = reviewDto.questionsId()
          .stream().map(questionService::getById).toList();
      questions.forEach(question -> question.getReviews().add(newReview));
      newReview.setQuestions(questions);
      reviewTypes.add(ReviewType.QUESTIONS);
    } else {
      newReview.setQuestions(new ArrayList<>());
    }

    if (reviewDto.flashCardsId() != null) {
      List<FlashCard> flashcards = reviewDto.flashCardsId().stream().map(
          flashCardService::getById
      ).toList();
      flashcards.forEach(flashCard -> flashCard.getReviews().add(newReview));
      newReview.setFlashcards(flashcards);
      reviewTypes.add(ReviewType.FLASH_CARDS);
    } else {
      newReview.setFlashcards(new ArrayList<>());
    }

    if (reviewDto.textId() != null) {
      UserText userText = userTextService.getById(reviewDto.textId());
      userText.getReviews().add(newReview);
      newReview.setUserText(userText);
      reviewTypes.add(ReviewType.TEXT);
    }

    newReview.setReviewTypes(reviewTypes);
    newReview.setTitle(reviewDto.title());
    newReview.setRegistrationDate(LocalDate.now());
    newReview.setProposedDate(reviewDto.proposedDate());
    return reviewRepository.save(newReview);
  }
}
