package hack.foodit.domain.review.service;

import hack.foodit.domain.post.entity.Post;
import hack.foodit.domain.post.entity.dto.PostRequestDTO;
import hack.foodit.domain.post.repository.PostRepository;
import hack.foodit.domain.review.entity.Review;
import hack.foodit.domain.review.entity.data.ReviewDto;
import hack.foodit.domain.review.entity.data.ReviewResponse;
import hack.foodit.domain.review.repository.ReviewRepository;
import hack.foodit.domain.review.repository.ReviewStatusRepository;
import hack.foodit.global.error.NotFoundException;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ReviewService {

  private final PostRepository postRepository;
  private final ReviewRepository reviewRepository;
  private final ReviewStatusRepository reviewStatusRepository;

  public ReviewResponse getReivewList(Long postId) {
    Post post = postRepository.findById(postId)
        .orElseThrow(NotFoundException::new);

    LocalTime localTime = LocalTime.now();
    int curHour = localTime.getHour();

    List<Review> reviews = reviewRepository.findAllByPost(post);
    List<ReviewDto> reviewDtos = new ArrayList<>();
    for (Review review : reviews) {
      reviewDtos.add(ReviewDto.builder()
          .hoursAgo(curHour - review.getCreatedAt().getHour())
          .name(review.getMember().getName())
          .content(review.getContent())
          .likeCount(review.getLikeCount())
          .build());
    }

    return ReviewResponse.builder()
        .totalElements(reviewDtos.size())
        .data(reviewDtos)
        .build();
  }
}
