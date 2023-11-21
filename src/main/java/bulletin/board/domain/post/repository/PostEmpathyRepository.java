package bulletin.board.domain.post.repository;

import bulletin.board.domain.member.Member;
import bulletin.board.domain.post.PostEmpathy;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PostEmpathyRepository extends JpaRepository<PostEmpathy, Long> {
    Optional<PostEmpathy> findByMemberAndPostId(Member member, Long postId);
}
