package hack.foodit.domain.post.entity.dto;

import hack.foodit.domain.post.entity.Post;
import jakarta.persistence.Column;
import lombok.*;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PostResponseDTO {
    Long id;
    String writer;
    String title;
    String content;
    Long likeCount;
    Long unlikeCount;
    Integer hoursAgo;
    List<String> imageUrls;
    List<Integer> categoryList;
    LocalDateTime createdAt;
    LocalDateTime modifiedAt;

    public static PostResponseDTO from(Post entity){
        return PostResponseDTO.builder()
                .id(entity.getId())
                .writer(entity.getMember().getName())
                .title(entity.getTitle())
                .content(entity.getContent())
                .likeCount(entity.getLikeCount())
                .unlikeCount(entity.getUnlikeCount())
                .hoursAgo((int)Duration.between(entity.getCreatedAt(),LocalDateTime.now()).toHours())
                .imageUrls(entity.getImageUrlList())
                .categoryList(entity.getCategoryList())
                .createdAt(entity.getCreatedAt())
                .modifiedAt(entity.getModifiedAt())
                .build();
    }
}
