package bulletin.board.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import bulletin.board.domain.Member;
import bulletin.board.constant.ErrorCode;
import bulletin.board.exceptions.LoginFailException;
import bulletin.board.repository.MemberRepository;
import bulletin.board.dto.LoginRequest;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class LoginService {

	private final MemberRepository memberRepository;

	@Transactional(readOnly = true)
	public Member login(LoginRequest loginRequest) {
		String loginId = loginRequest.getLoginId();
		String password = loginRequest.getPassword();

		return memberRepository.findByLoginIdAndPassword(loginId, password)
				.orElseThrow(() -> new LoginFailException(ErrorCode.BAD_LOGIN));
	}
}
