package hack.foodit.domain.post.entity;

import hack.foodit.global.entity.BaseEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class PostStatus extends BaseEntity {

  @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  // TODO : User 테이블 연관관계 매핑
//  @ManyToOne(fetch = FetchType.LAZY)
//  @JoinColumn(name = "")
//  private

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "post_id")
  private Post post;

  /**
   * True: like
   * False: unlike
   */
  private Boolean status;

  public void setToLikeStatus() {
    this.status = Boolean.TRUE;
  }

  public void setToUnlikeStatus() {
    this.status = Boolean.FALSE;
  }
}
