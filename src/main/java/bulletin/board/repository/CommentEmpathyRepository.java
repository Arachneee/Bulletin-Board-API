package bulletin.board.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import bulletin.board.domain.CommentEmpathy;
import bulletin.board.domain.Member;

public interface CommentEmpathyRepository extends JpaRepository<CommentEmpathy, Long> {

	@Query("select ce from CommentEmpathy ce where ce.member = :member and ce.comment.id = :commentId")
	Optional<CommentEmpathy> findByMemberAndCommentId(@Param("member") Member member, @Param("commentId") Long commentId);
}
