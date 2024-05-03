package hack.foodit.domain.review.repository;

import hack.foodit.domain.post.entity.Post;
import hack.foodit.domain.review.entity.Review;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReviewRepository extends JpaRepository<Review, Long> {

  List<Review> findAllByPost(Post post);

}
