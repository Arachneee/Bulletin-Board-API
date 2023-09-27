package bulletin.board.core.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import bulletin.board.core.domain.CommentEmpathy;

public interface CommentEmpathyRepository extends JpaRepository<CommentEmpathy, Long> {
}
