package hack.foodit.domain.post.entity.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class ToggleRequestDto {

  private Long userId;

  private Long postId;
}
