package hack.foodit.domain.post.entity.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class PostRequestDTO {
    String title;
    List<Integer> categoryList;
    String content;
}
