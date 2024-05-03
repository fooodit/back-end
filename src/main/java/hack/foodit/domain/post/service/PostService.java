package hack.foodit.domain.post.service;

import com.sun.jdi.InternalException;
import hack.foodit.domain.member.entity.Member;
import hack.foodit.domain.member.repository.MemberRepository;
import hack.foodit.domain.post.entity.dto.ToggleRequestDto;
import hack.foodit.domain.post.entity.Post;
import hack.foodit.domain.post.entity.PostStatus;
import hack.foodit.domain.post.entity.dto.PostRequestDTO;
import hack.foodit.domain.post.entity.dto.PostResponseDTO;
import hack.foodit.domain.post.repository.PostRepository;
import hack.foodit.domain.post.repository.PostStatusRepository;
import hack.foodit.global.error.NotFoundException;
import hack.foodit.global.service.S3Service;
import jakarta.persistence.EntityNotFoundException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
@Slf4j
public class PostService {

  private final PostRepository postRepository;
  private final PostStatusRepository postStatusRepository;
  private final MemberRepository memberRepository;
  private final S3Service s3Service;

  @Transactional
  public Long createPost(PostRequestDTO postRequestDTO, List<MultipartFile> multipartFileList) {
    List<String> imageUrlList = new ArrayList<>();
    for (MultipartFile multipartFile : multipartFileList) {
      try {
        imageUrlList
            .add(s3Service.uploadOriginImage(multipartFile, multipartFile.getOriginalFilename()));
      } catch (Exception e) {
        throw new InternalException();
      }

    }

    Post post = new Post();
    post.setTitle(postRequestDTO.getTitle());
    post.setContent(postRequestDTO.getContent());
    post.setCategoryList(postRequestDTO.getCategoryList());
    post.setModifiedAt(null);
    post.setCreatedAt(LocalDateTime.now());
    post.setLikeCount(0L);
    post.setUnlikeCount(0L);
    post.setImageUrlList(imageUrlList);
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

  public List<PostResponseDTO> findTrending(){
    List<Post> posts = postRepository.findTop5ByOrderByLikeCountDesc();
    return posts.stream()
            .map(PostResponseDTO::from)
            .collect(Collectors.toList());
  }
  @Transactional
  public PostResponseDTO likeToggle(ToggleRequestDto requestDto) {
    Long memberId = requestDto.getMemberId();
    Long postId = requestDto.getPostId();

    Post post = postRepository.findById(postId)
        .orElseThrow(NotFoundException::new);

    Member member = memberRepository.findById(memberId)
        .orElseThrow(NotFoundException::new);

    PostStatus postStatus = postStatusRepository.findByMemberAndPost(member, post);

    // None 상태 -> like 상태
    if (postStatus == null) {
      postStatus = PostStatus.builder()
          .status(Boolean.TRUE)
          .member(member)
          .post(post)
          .build();
      postStatusRepository.save(postStatus);

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

    return PostResponseDTO.from(post);
  }

  @Transactional
  public PostResponseDTO unlikeToggle(ToggleRequestDto requestDto) {
    Long userId = requestDto.getMemberId();
    Long postId = requestDto.getPostId();

    Post post = postRepository.findById(postId)
        .orElseThrow(NotFoundException::new);

    Member member = memberRepository.findById(userId)
        .orElseThrow(NotFoundException::new);

    PostStatus postStatus = postStatusRepository.findByMemberAndPost(member, post);

    // None 상태 -> unlike 상태
    if (postStatus == null) {
      postStatus = PostStatus.builder()
          .status(Boolean.FALSE)
          .member(member)
          .post(post)
          .build();
      postStatusRepository.save(postStatus);

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

    return PostResponseDTO.from(post);
  }
}
