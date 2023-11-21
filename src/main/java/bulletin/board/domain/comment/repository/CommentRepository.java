package bulletin.board.domain.comment.repository;

import java.util.Optional;

import bulletin.board.domain.member.Member;
import jakarta.persistence.LockModeType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;

import bulletin.board.domain.comment.Comment;

public interface CommentRepository extends JpaRepository<Comment, Long>, CommentQueryDslRepository {
	@EntityGraph(attributePaths = {"member"})
	Page<Comment> findWithMemberByPostIdAndParentCommentIsNull(Long postId, Pageable pageable);

	@EntityGraph(attributePaths = {"member", "commentEmpathies"})
	Optional<Comment> findWithMemberAndEmpathyById(Long id);

	@Lock(LockModeType.PESSIMISTIC_WRITE)
	@EntityGraph(attributePaths = {"commentEmpathies"})
	Optional<Comment> findWithEmpathyById(Long id);

	Boolean existsByIdAndMember(Long id, Member member);

	@Lock(LockModeType.PESSIMISTIC_WRITE)
	@EntityGraph(attributePaths = {"post"})
    Optional<Comment> findWithPostById(Long id);
}
