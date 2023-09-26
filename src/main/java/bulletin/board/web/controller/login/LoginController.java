package bulletin.board.web.controller.login;


import java.util.HashMap;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import bulletin.board.domain.Member;
import bulletin.board.dto.LoginRequest;
import bulletin.board.service.LoginService;
import bulletin.board.web.session.SessionConst;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class LoginController {

	private final LoginService loginService;

	@PostMapping("/login")
	public ResponseEntity<Map<String,String>> login(@RequestBody @Valid LoginRequest loginRequest,
						@RequestParam(defaultValue = "/") String redirectURL,
						HttpServletRequest request) {
		Member member = loginService.login(loginRequest);

		request.getSession()
				.setAttribute(SessionConst.LOGIN_MEMBER, member);

		return ResponseEntity.ok().body(Map.of("redirectURL", redirectURL));
	}

	@DeleteMapping("/login")
	public ResponseEntity<Void> logout(HttpServletRequest request) {
		HttpSession session = request.getSession(false);
		if (session != null) {
			session.invalidate();
		}
		return ResponseEntity.noContent().build();
	}
}
