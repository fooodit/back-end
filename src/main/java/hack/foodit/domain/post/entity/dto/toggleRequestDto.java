package hack.foodit.domain.post.entity.dto;

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
