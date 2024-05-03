package hack.foodit.domain.review.controller;

import hack.foodit.domain.review.service.ReviewService;
import hack.foodit.global.entity.BaseEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/review")
@RequiredArgsConstructor
public class ReviewController {

  private final ReviewService reviewService;

  @GetMapping("")
  public ResponseEntity<?> getReviews(Long postId) {
    return new ResponseEntity<>(reviewService.getReivewList(postId), HttpStatus.OK);
  }
}
