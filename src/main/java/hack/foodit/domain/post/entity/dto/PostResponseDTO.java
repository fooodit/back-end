package hack.foodit.domain.post.entity.dto;

import hack.foodit.domain.post.entity.Post;
import jakarta.persistence.Column;
import lombok.*;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PostResponseDTO {
    Long id;
    String title;
    String content;
    Long likeCount;
    Long unlikeCount;
    List<Integer> categoryList;
    LocalDateTime createdAt;
    LocalDateTime modifiedAt;
    public static PostResponseDTO from(Post entity){
        return PostResponseDTO.builder()
                .id(entity.getId())
                .title(entity.getTitle())
                .content(entity.getContent())
                .likeCount(entity.getLikeCount())
                .unlikeCount(entity.getUnlikeCount())
                .categoryList(entity.getCategoryList())
                .createdAt(entity.getCreatedAt())
                .modifiedAt(entity.getModifiedAt())
                .build();
    }
}
