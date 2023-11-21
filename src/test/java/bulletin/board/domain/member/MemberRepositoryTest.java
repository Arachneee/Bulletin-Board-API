package bulletin.board.domain.member;

import static org.assertj.core.api.Assertions.*;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import bulletin.board.IntegrationTestSupport;

@DisplayName("회원 저장소는")
@Transactional
class MemberRepositoryTest extends IntegrationTestSupport {

	@Autowired
	private MemberRepository memberRepository;

	@BeforeEach
	void setUp() {
		Member member = Member.create("abc", "1234", "ABC");
		memberRepository.save(member);
	}

	@DisplayName("로그인 ID와 Password로 회원을 찾을 수 있다.")
	@Test
	void findByLoginIdAndPassword() {
		// given // when
		Optional<Member> findMember = memberRepository.findByLoginIdAndPassword("abc", "1234");

		// then
		assertThat(findMember.get())
			.extracting("loginId", "password", "name")
			.containsExactly("abc", "1234", "ABC");
	}

	@Nested
	@DisplayName("로그인ID가 존재하는지 확인할 수 있다.")
	class ExistsByLoginId {

		@DisplayName("True")
		@Test
		void exists() {
			// given // when
			Boolean exists = memberRepository.existsByLoginId("abc");

			// then
			assertThat(exists).isTrue();
		}

		@DisplayName("False")
		@Test
		void none() {
			// given // when
			Boolean exists = memberRepository.existsByLoginId("ab");

			// then
			assertThat(exists).isFalse();
		}
	}

	@Nested
	@DisplayName("닉네임이 존재하는지 확인할 수 있다.")
	class ExistsByName {

		@DisplayName("True")
		@Test
		void exists() {
			// given // when
			Boolean exists = memberRepository.existsByName("ABC");

			// then
			assertThat(exists).isTrue();
		}

		@DisplayName("False")
		@Test
		void none() {
			// given // when
			Boolean exists = memberRepository.existsByLoginId("AB");

			// then
			assertThat(exists).isFalse();
		}
	}
}