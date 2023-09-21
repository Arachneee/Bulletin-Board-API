package bulletin.board.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import bulletin.board.domain.Comment;

public interface CommentRepository extends JpaRepository<Comment, Long> {
}
