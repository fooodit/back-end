package hack.foodit.domain.post.service;

import hack.foodit.domain.post.entity.dto.toggleRequestDto;
import hack.foodit.domain.post.entity.Post;
import hack.foodit.domain.post.entity.PostStatus;
import hack.foodit.domain.post.entity.dto.PostRequestDTO;
import hack.foodit.domain.post.entity.dto.PostResponseDTO;
import hack.foodit.domain.post.repository.PostRepository;
import hack.foodit.domain.post.repository.PostStatusRepository;
import hack.foodit.global.error.NotFoundException;
import jakarta.persistence.EntityNotFoundException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
public class PostService {

  private PostRepository postRepository;
  private PostStatusRepository postStatusRepository;

  @Transactional
  public Long createPost(PostRequestDTO postRequestDTO) {
    Post post = new Post();
    post.setTitle(postRequestDTO.getTitle());
    post.setContent(postRequestDTO.getContent());
    post.setCategoryList(postRequestDTO.getCategoryList());
    post.setModifiedAt(null);
    post.setCreatedAt(LocalDateTime.now());
    post.setLikeCount(0L);
    post.setUnlikeCount(0L);
    postRepository.save(post);
    return post.getId();
  }

  @Transactional
  public void deletePost(Long id) {
    Optional<Post> post = postRepository.findById(id);
    postRepository.delete(post.get());
  }

  @Transactional
  public void updatePost(Long id, PostRequestDTO postRequestDTO) {
    Optional<Post> optionalPost = postRepository.findById(id);
    optionalPost.ifPresent(post -> {
      post.setContent(postRequestDTO.getContent());
      post.setCategoryList(postRequestDTO.getCategoryList());
      post.setTitle(postRequestDTO.getTitle());
      post.setModifiedAt(LocalDateTime.now());
    });
  }

  public PostResponseDTO findPostById(Long id) {
    Post post = postRepository.findById(id)
        .orElseThrow(() -> new EntityNotFoundException("게시물이 존재하지 않습니다."));
    return PostResponseDTO.from(post);
  }

  public List<PostResponseDTO> findPostByCategory(List<Integer> categoryList, Pageable pageable) {
    Page<Post> posts = postRepository.findByCategoryListIn(categoryList, pageable);
    return posts.stream()
        .map(PostResponseDTO::from)
        .collect(Collectors.toList());
  }

  public List<PostResponseDTO> findAll(Pageable pageable) {
    Page<Post> posts = postRepository.findAll(pageable);
    return posts.stream()
        .map(PostResponseDTO::from)
        .collect(Collectors.toList());
  }

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
