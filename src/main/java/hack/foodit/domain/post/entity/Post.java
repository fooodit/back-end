package hack.foodit.domain.post.entity;

import hack.foodit.domain.member.entity.Member;
import hack.foodit.global.entity.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import java.util.List;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Post extends BaseEntity {

  @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "user_id")
  private Member member;

  @Column(columnDefinition = "VARCHAR(1024)")
  private String title;

  @Column(columnDefinition = "TEXT")
  private String content;

  private Long likeCount;

  private Long unlikeCount;

  @ElementCollection
  private List<Integer> categoryList;

  public void incrementLikeCount() {
    this.likeCount += 1;
  }

  public void decrementLikeCount() {
    this.likeCount -= 1;
  }

  public void incrementUnlikeCount() {
    this.unlikeCount += 1;
  }

  public void decrementUnlikeCount() {
    this.unlikeCount -= 1;
  }
}
