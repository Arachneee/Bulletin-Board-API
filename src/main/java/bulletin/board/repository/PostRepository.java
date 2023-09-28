package bulletin.board.repository;


import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import bulletin.board.domain.Post;

public interface PostRepository extends JpaRepository<Post, Long> {

	@EntityGraph(attributePaths = {"member"})
	Page<Post> findByTitleContains(@Param("title") String searchString, Pageable pageable);

	@EntityGraph(attributePaths = {"member"})
	Page<Post> findByContentContains(@Param("content") String searchString, Pageable pageable);

	@EntityGraph(attributePaths = {"member"})
	@Query("select p from Post p where p.member.name like %:name%")
	Page<Post> findByNameContains(@Param("name") String searchString, Pageable pageable);

	@EntityGraph(attributePaths = {"member"})
	Optional<Post> findWithMemberById(@Param("id") Long id);
}
