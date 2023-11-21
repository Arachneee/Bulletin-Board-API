package bulletin.board.domain.post.repository;


import java.util.Optional;

import bulletin.board.domain.member.Member;
import bulletin.board.domain.post.Post;
import bulletin.board.domain.post.repository.PostQueryDslRepository;
import jakarta.persistence.LockModeType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface PostRepository extends JpaRepository<Post, Long>, PostQueryDslRepository {

	@EntityGraph(attributePaths = {"member"})
	Page<Post> findByTitleContains(@Param("title") String searchString, Pageable pageable);

	@EntityGraph(attributePaths = {"member"})
	Page<Post> findByContentContains(@Param("content") String searchString, Pageable pageable);

	@EntityGraph(attributePaths = {"member"})
	@Query("select p from Post p where p.member.name like %:name%")
	Page<Post> findByNameContains(@Param("name") String searchString, Pageable pageable);

	@Lock(LockModeType.PESSIMISTIC_WRITE)
	@EntityGraph(attributePaths = {"member", "images"})
	Optional<Post> findWithMemberAndImagesById(Long id);

	@Lock(LockModeType.PESSIMISTIC_WRITE)
	@EntityGraph(attributePaths = {"comments"})
	Optional<Post> findWithCommentsById(Long id);

	Boolean existsByIdAndMember(Long id, Member member);

	@Lock(LockModeType.PESSIMISTIC_WRITE)
	@EntityGraph(attributePaths = {"postEmpathies"})
    Optional<Post> findWithEmpathyById(Long id);
}
