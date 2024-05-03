package hack.foodit.domain.post.repository;

import hack.foodit.domain.member.entity.Member;
import hack.foodit.domain.post.entity.Post;
import hack.foodit.domain.post.entity.PostStatus;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

public interface PostStatusRepository extends JpaRepository<PostStatus, Long> {

   PostStatus findByMemberAndPost(Member member, Post post);
}
