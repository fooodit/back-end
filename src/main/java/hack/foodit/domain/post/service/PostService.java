package hack.foodit.domain.post.service;

import hack.foodit.domain.post.entity.Post;
import hack.foodit.domain.post.entity.dto.PostRequestDTO;
import hack.foodit.domain.post.entity.dto.PostResponseDTO;
import hack.foodit.domain.post.repository.PostRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class PostService {
    private final PostRepository postRepository;

    @Transactional
    public Long createPost(PostRequestDTO postRequestDTO){
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
    public void deletePost(Long id){
        Optional<Post> post = postRepository.findById(id);
        postRepository.delete(post.get());
    }

    @Transactional
    public void updatePost(Long id, PostRequestDTO postRequestDTO){
        Optional<Post> optionalPost = postRepository.findById(id);
        optionalPost.ifPresent(post->{
            post.setContent(postRequestDTO.getContent());
            post.setCategoryList(postRequestDTO.getCategoryList());
            post.setTitle(postRequestDTO.getTitle());
            post.setModifiedAt(LocalDateTime.now());
        });
    }
    public PostResponseDTO findPostById(Long id){
        Post post = postRepository.findById(id)
                .orElseThrow(()->new EntityNotFoundException("게시물이 존재하지 않습니다."));
        return PostResponseDTO.from(post);
    }
    public List<PostResponseDTO> findPostByCategory(List<Integer> categoryList, Pageable pageable){
        Page<Post> posts = postRepository.findByCategoryListIn(categoryList, pageable);
        return posts.stream()
                .map(PostResponseDTO::from)
                .collect(Collectors.toList());
    }

    public List<PostResponseDTO> findAll(Pageable pageable){
        Page<Post> posts = postRepository.findAll(pageable);
        return posts.stream()
                .map(PostResponseDTO::from)
                .collect(Collectors.toList());
    }
}
