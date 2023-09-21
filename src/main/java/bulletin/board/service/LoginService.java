package bulletin.board.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import bulletin.board.domain.Member;
import bulletin.board.repository.MemberRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class LoginService {

	private final MemberRepository memberRepository;

	public Member login(String loginId, String password) {
		return memberRepository.findByLoginId(loginId)
			.filter(member -> member.isRightPassword(password))
			.orElse(null);
	}
}
