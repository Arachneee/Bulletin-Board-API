package bulletin.board.api;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import bulletin.board.core.domain.Member;
import bulletin.board.api.login.argumentresolver.Login;
import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class HomeController {
	@GetMapping("/")
	public String home(@Login Member loginMember,
					   Model model) {

		if (loginMember == null) {
			return "loginHome";
		}

		model.addAttribute("member", loginMember);
		return "post/posts";
	}
}
