package bulletin.board.web.controller.member;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import bulletin.board.service.MemberService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/members")
public class MemberController {
	private final MemberService memberService;

	//Create
	@PostMapping("")
	public ResponseEntity createMember(@Valid MemberCreateDto memberCreateDto, BindingResult bindingResult) {
		if (bindingResult.hasErrors() || validateMemberAddForm(memberCreateDto, bindingResult)) {
			return "members/addMemberForm";
		}
		memberService.save(memberCreateDto.getLoginId(), memberCreateDto.getPassword(), memberCreateDto.getName());
		return "redirect:/";
	}

	private boolean validateMemberAddForm(MemberCreateDto memberCreateDto, BindingResult bindingResult) {
		if (isSamePassword(memberCreateDto.getPassword(), memberCreateDto.getPasswordRe())) {
			bindingResult.rejectValue("passwordRe","reject" ,"비밀번호가 일치하지 않습니다.");
		}

		if (memberService.isDuplicatedLoginId(memberCreateDto.getLoginId())) {
			bindingResult.rejectValue("loginId","duplicated" ,"중복된 아이디입니다.");
		}

		if (memberService.isDuplicatedName(memberCreateDto.getName())) {
			bindingResult.rejectValue("name","duplicated" ,"중복된 이름입니다.");
		}

		if (bindingResult.hasErrors()) {
			return true;
		}

		return false;
	}

	private static boolean isSamePassword(String password, String passwordRe) {
		return !password.equals(passwordRe);
	}
}
