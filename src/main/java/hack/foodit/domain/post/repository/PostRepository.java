package hack.foodit.domain.post.repository;

import hack.foodit.domain.post.entity.Post;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

public interface PostRepository extends JpaRepository<Post, Long> {

    Page<Post> findAll(Pageable pageable);

    Optional<Post> findById(Long id);

    Page<Post> findByCategoryListIn(List<Integer> categoryList, Pageable pageable);

    List<Post> findTop5ByOrderByLikeCountDesc();
}
