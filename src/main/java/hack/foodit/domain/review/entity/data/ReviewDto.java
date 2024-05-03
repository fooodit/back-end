package hack.foodit.domain.review.entity.data;

import hack.foodit.domain.review.entity.Review;
import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ReviewDto {

  private String name;
  private String content;
  private Long likeCount;
  private LocalDateTime createdAt;
}
