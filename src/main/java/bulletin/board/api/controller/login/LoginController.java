package bulletin.board.api.controller.login;


import bulletin.board.api.service.login.response.LoginResponse;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import bulletin.board.domain.member.Member;
import bulletin.board.api.controller.login.request.LoginRequest;
import bulletin.board.api.service.login.LoginService;
import bulletin.board.api.service.login.SessionConst;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/loginn")
public class LoginController {

	private final LoginService loginService;

	@PostMapping("")
	public ResponseEntity<LoginResponse> login(@Valid @RequestBody LoginRequest loginRequest,
											   HttpServletRequest request) {
		Member loginMember = loginService.login(loginRequest);

		request.getSession().setAttribute(SessionConst.LOGIN_MEMBER, loginMember);

		return ResponseEntity.ok().body(LoginResponse.login(loginMember));
	}

	@DeleteMapping("")
	public ResponseEntity<Void> logout(HttpServletRequest request) {
		HttpSession session = request.getSession(false);
		if (session != null) {
			session.invalidate();
		}
		return ResponseEntity.noContent().build();
	}
}
