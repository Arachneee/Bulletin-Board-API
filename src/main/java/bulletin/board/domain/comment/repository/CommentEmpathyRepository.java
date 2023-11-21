package bulletin.board.domain.comment.repository;

import java.util.Optional;

import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import bulletin.board.domain.comment.CommentEmpathy;
import bulletin.board.domain.member.Member;

public interface CommentEmpathyRepository extends JpaRepository<CommentEmpathy, Long> {

	@Lock(LockModeType.PESSIMISTIC_WRITE)
	@Query("select ce from CommentEmpathy ce where ce.member = :member and ce.comment.id = :commentId")
	Optional<CommentEmpathy> findByMemberAndCommentId(@Param("member") Member member, @Param("commentId") Long commentId);
}
