package hack.foodit.domain.review.entity.data;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class ReviewResponse {

  private Integer totalElements;
  private List<ReviewDto> data;
}
