package hack.foodit.domain.post.service;

import hack.foodit.domain.post.data.request.toggleRequestDto;
import hack.foodit.domain.post.entity.Post;
import hack.foodit.domain.post.entity.PostStatus;
import hack.foodit.domain.post.repository.PostRepository;
import hack.foodit.domain.post.repository.PostStatusRepository;
import hack.foodit.global.error.NotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
public class PostService {

  private PostRepository postRepository;
  private PostStatusRepository postStatusRepository;

  @Transactional
  public String likeToggle(toggleRequestDto requestDto) {
    Long userId = requestDto.getUserId();
    Long postId = requestDto.getPostId();
    Integer category = requestDto.getCategory();

    Post post = postRepository.findById(postId)
        .orElseThrow(NotFoundException::new);

    // TODO : User user = ...findById

    // TODO : findByUserAndPost
    // postStatusRepository.findBy

    PostStatus postStatus = PostStatus.builder()
        .status(Boolean.TRUE)
        .build();

    // None 상태 -> like 상태
    if (postStatus == null) {
      postStatus = PostStatus.builder()
          .status(Boolean.TRUE)
          // TODO : user 추가
          .post(post)
          .build();

      // 좋아요 수 + 1
      post.incrementLikeCount();
    }
    // like 상태 -> like 상태 해제
    else if (postStatus.getStatus()) {
      postStatusRepository.delete(postStatus);

      // 좋아요 수 - 1
      post.decrementLikeCount();
    }
    // unlike 상태 -> like 상태
    else {
      postStatus.setToLikeStatus();

      // 싫어요 수 - 1, 좋아요 수 + 1
      post.decrementUnlikeCount();
      post.incrementLikeCount();
    }

    return null;
  }

  @Transactional
  public String unlikeToggle(toggleRequestDto requestDto) {
    Long userId = requestDto.getUserId();
    Long postId = requestDto.getPostId();
    Integer category = requestDto.getCategory();

    Post post = postRepository.findById(postId)
        .orElseThrow(NotFoundException::new);

    // TODO : User user = ...findById

    // TODO : findByUserAndPost
    // postStatusRepository.findBy

    PostStatus postStatus = PostStatus.builder()
        .status(Boolean.TRUE)
        .build();

    // None 상태 -> unlike 상태
    if (postStatus == null) {
      postStatus = PostStatus.builder()
          .status(Boolean.FALSE)
          // TODO : user 추가
          .post(post)
          .build();

      // 싫어요 수 + 1
      post.incrementUnlikeCount();
    }
    // like 상태 -> unlike 상태
    else if (postStatus.getStatus()) {
      postStatus.setToUnlikeStatus();

      // 좋아요 수 - 1, 싫어요 수 + 1
      post.decrementLikeCount();
      post.incrementUnlikeCount();
    }
    // unlike 상태 -> unlike 상태 해제
    else {
      postStatusRepository.delete(postStatus);

      // 싫어요 수 - 1
      post.decrementUnlikeCount();
    }

    return null;
  }
}
