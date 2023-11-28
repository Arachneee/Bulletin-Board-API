package bulletin.board.domain.member;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {
	Optional<Member> findByLoginIdAndPassword(final String loginId, final String password);

	Boolean existsByLoginId(final String loginId);

	Boolean existsByName(final String name);

	Optional<Member> findByLoginId(final String loginId);
}
