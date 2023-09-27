package bulletin.board.api.login;


import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import bulletin.board.core.domain.Member;
import bulletin.board.core.dto.LoginRequest;
import bulletin.board.core.service.LoginService;
import bulletin.board.api.session.SessionConst;
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
	public ResponseEntity<Map<String,String>> login(@RequestBody @Valid LoginRequest loginRequest,
						@RequestParam(defaultValue = "/") String redirectURL,
						HttpServletRequest request) {
		Member member = loginService.login(loginRequest);

		request.getSession()
				.setAttribute(SessionConst.LOGIN_MEMBER, member);

		return ResponseEntity.ok().body(Map.of("redirectURL", redirectURL));
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
