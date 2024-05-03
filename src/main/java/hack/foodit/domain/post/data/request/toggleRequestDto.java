package hack.foodit.domain.post.data.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class toggleRequestDto {

  private Long userId;

  private Long postId;

  private Integer category;
}
