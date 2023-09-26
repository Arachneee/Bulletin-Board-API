package bulletin.board.web.controller.member;

import java.net.URI;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import bulletin.board.dto.MemberRequest;
import bulletin.board.dto.MemberResponse;
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
	public ResponseEntity<Void> createMember(@RequestBody @Valid MemberRequest memberRequest) {
		Long saveId = memberService.save(memberRequest);
		return ResponseEntity.created(URI.create("/members/" + saveId)).build();
	}

	//Read
	@GetMapping("/{id}")
	public ResponseEntity<MemberResponse> findMember(@PathVariable("id") Long memberId) {
		return ResponseEntity.ok().body(memberService.findMember(memberId));
	}

	//Update


	//Delete



}
