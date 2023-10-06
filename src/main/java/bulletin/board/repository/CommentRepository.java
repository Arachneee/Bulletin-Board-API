package bulletin.board.repository;

import java.util.Optional;

import bulletin.board.domain.Member;
import jakarta.persistence.LockModeType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.repository.query.Param;

import bulletin.board.domain.Comment;

public interface CommentRepository extends JpaRepository<Comment, Long> {
	@EntityGraph(attributePaths = {"member"})
	Page<Comment> findWithMemberByPostId(Long postId, Pageable pageable);

	@EntityGraph(attributePaths = {"member", "commentEmpathies"})
	Optional<Comment> findWithMemberAndEmpathyById(Long id);

	@Lock(LockModeType.PESSIMISTIC_WRITE)
	@EntityGraph(attributePaths = {"commentEmpathies"})
	Optional<Comment> findWithEmpathyById(Long id);

	@EntityGraph(attributePaths = {"member", "commentEmpathies"})
	Optional<Comment> findTop1WithMemberAndEmpathyByPostId(Long postId);

	Boolean existsByIdAndMember(Long id, Member member);
}
