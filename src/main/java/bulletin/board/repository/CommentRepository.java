package bulletin.board.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import bulletin.board.domain.Comment;

public interface CommentRepository extends JpaRepository<Comment, Long> {
	@EntityGraph(attributePaths = {"member"})
	Page<Comment> findWithMemberByPostId(@Param("postId") Long postId, Pageable pageable);
}
