package hack.foodit.domain.post.repository;

import hack.foodit.domain.post.entity.PostStatus;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostStatusRepository extends JpaRepository<PostStatus, Long> {

  // Optional<PostStatus> findByUser
}
