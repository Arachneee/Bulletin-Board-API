package bulletin.board.repository;

import bulletin.board.domain.Member;
import bulletin.board.domain.PostEmpathy;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PostEmpathyRepository extends JpaRepository<PostEmpathy, Long> {
    Optional<PostEmpathy> findByMemberAndPostId(Member member, Long postId);
}
