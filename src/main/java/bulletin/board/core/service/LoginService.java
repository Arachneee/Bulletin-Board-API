package bulletin.board.core.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import bulletin.board.core.domain.Member;
import bulletin.board.core.common.exceptions.ErrorCode;
import bulletin.board.core.common.exceptions.LoginFailException;
import bulletin.board.core.repository.MemberRepository;
import bulletin.board.core.dto.LoginRequest;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class LoginService {

	private final MemberRepository memberRepository;

	public Member login(LoginRequest loginRequest) {
		String loginId = loginRequest.getLoginId();
		String password = loginRequest.getPassword();

		return memberRepository.findByLoginIdAndPassword(loginId, password)
			.orElseThrow(() -> new LoginFailException(ErrorCode.BAD_LOGIN));
	}
}
