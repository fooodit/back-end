package hack.foodit.domain.review.repository;

import hack.foodit.domain.review.entity.ReviewStatus;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReviewStatusRepository extends JpaRepository<ReviewStatus, Long> {

}
