package bulletin.board.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import bulletin.board.domain.Post;

public interface PostRepository extends JpaRepository<Post, Long> {
}
