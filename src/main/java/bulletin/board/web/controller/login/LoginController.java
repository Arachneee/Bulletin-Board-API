package bulletin.board.web.controller.login;

import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import bulletin.board.domain.Member;
import bulletin.board.service.LoginService;
import bulletin.board.web.session.SessionConst;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class LoginController {

	private final LoginService loginService;

	@PostMapping("/login")
	public String login(@Valid LoginDto loginDto, BindingResult bindingResult,
						@RequestParam(defaultValue = "/") String redirectURL,
						HttpServletRequest request) {

		if (bindingResult.hasErrors()) {
			return "login/loginForm";
		}

		Member member = loginService.login(loginDto.getLoginId(), loginDto.getPassword());

		if (member == null) {
			bindingResult.reject("loginFail", "아이디 또는 비밀번호가 맞지 않습니다.");
			return "login/loginForm";
		}

		request.getSession()
				.setAttribute(SessionConst.LOGIN_MEMBER, member);

		return "redirect:" + redirectURL;
	}

	@PostMapping("/logout")
	public String logout(HttpServletRequest request) {
		HttpSession session = request.getSession(false);
		if (session != null) {
			session.invalidate();
		}

		return "redirect:/";
	}
}
