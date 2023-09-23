package bulletin.board.web.controller;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import bulletin.board.domain.Member;
import bulletin.board.web.argumentresolver.Login;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class HomeController {
	@GetMapping("/")
	public String home(@Login Member loginMember,
					   Model model) {

		if (loginMember == null) {
			return "loginHome";
		}

		model.addAttribute("member", loginMember);
		return "redirect:/posts";
	}
}
