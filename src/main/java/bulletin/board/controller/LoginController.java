package bulletin.board.controller;


import bulletin.board.dto.LoginResponse;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import bulletin.board.domain.Member;
import bulletin.board.dto.LoginRequest;
import bulletin.board.service.LoginService;
import bulletin.board.constant.SessionConst;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/login")
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
