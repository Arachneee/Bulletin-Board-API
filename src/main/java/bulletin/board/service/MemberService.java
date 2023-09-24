package bulletin.board.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import bulletin.board.domain.Member;
import bulletin.board.repository.MemberRepository;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
@Transactional
public class MemberService {

	private final MemberRepository memberRepository;

	public boolean isDuplicatedLoginId(String loginId) {
		return memberRepository.countLoginId(loginId) >= 1;
	}

	public boolean isDuplicatedName(String name) {
		return memberRepository.countName(name) >= 1;
	}

	public void save(String loginId, String password, String name) {
		memberRepository.save(Member.create(loginId, password, name));
	}
}
