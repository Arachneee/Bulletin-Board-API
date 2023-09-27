package bulletin.board.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import bulletin.board.domain.Member;
import bulletin.board.domain.Post;

public interface PostRepository extends JpaRepository<Post, Long> {
	Optional<Post> findByIdAndMember(@Param("id") Long postId, @Param("member") Member member);
}
