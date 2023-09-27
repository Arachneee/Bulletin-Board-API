package bulletin.board.core.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import bulletin.board.core.domain.Member;

public interface MemberRepository extends JpaRepository<Member, Long> {
	Optional<Member> findByLoginId(@Param("longId") String loginId);
	Optional<Member> findByName(@Param("name") String name);

	Optional<Member> findByLoginIdAndPassword(@Param("longId") String loginId, @Param("password") String password);
}
