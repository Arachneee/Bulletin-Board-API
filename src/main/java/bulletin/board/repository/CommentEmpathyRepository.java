package bulletin.board.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import bulletin.board.domain.CommentEmpathy;

public interface CommentEmpathyRepository extends JpaRepository<CommentEmpathy, Long> {
}
