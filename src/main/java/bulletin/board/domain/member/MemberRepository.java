package bulletin.board.domain.member;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import bulletin.board.domain.member.Member;

public interface MemberRepository extends JpaRepository<Member, Long> {
	Optional<Member> findByLoginIdAndPassword(String loginId, String password);

	Boolean existsByLoginId(String loginId);

	Boolean existsByName(String name);
}
