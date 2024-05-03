package hack.foodit.domain.review.service;

import hack.foodit.domain.post.entity.Post;
import hack.foodit.domain.post.entity.dto.PostRequestDTO;
import hack.foodit.domain.post.repository.PostRepository;
import hack.foodit.domain.review.entity.Review;
import hack.foodit.domain.review.entity.data.ReviewDto;
import hack.foodit.domain.review.repository.ReviewRepository;
import hack.foodit.domain.review.repository.ReviewStatusRepository;
import hack.foodit.global.error.NotFoundException;
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

  public List<ReviewDto> getReivewList(Long postId) {
    Post post = postRepository.findById(postId)
        .orElseThrow(NotFoundException::new);

    List<Review> reviews = reviewRepository.findAllByPost(post);
    List<ReviewDto> reviewDtos = new ArrayList<>();
    for (Review review : reviews) {
      reviewDtos.add(ReviewDto.builder()
          .name(review.getMember().getName())
          .content(review.getContent())
          .likeCount(review.getLikeCount())
          .build());
    }

    return reviewDtos;
  }
}
