package bulletin.board.api.controller.member;

import java.net.URI;
import java.util.Map;

import bulletin.board.api.controller.member.request.MemberNameRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import bulletin.board.api.controller.member.request.MemberRequest;
import bulletin.board.api.service.member.response.MemberResponse;
import bulletin.board.api.service.member.MemberService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/members")
public class MemberController {

	private final MemberService memberService;

	@PostMapping("")
	public ResponseEntity<Void> createMember(@Valid @RequestBody MemberRequest memberRequest) {
		Long saveId = memberService.create(memberRequest);

		return ResponseEntity.created(URI.create("/members/" + saveId)).build();
	}

	@GetMapping("/{id}")
	public ResponseEntity<MemberResponse> findMember(@PathVariable("id") Long id) {
		return ResponseEntity.ok().body(memberService.findById(id));
	}

	@PatchMapping("/{id}")
	public ResponseEntity<Void> updateName(@PathVariable("id") Long id, @Valid @RequestBody MemberNameRequest memberNameRequest) {
		memberService.updateName(id, memberNameRequest);

		return ResponseEntity.ok().build();
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deleteMember(@PathVariable("id") Long id) {
		memberService.delete(id);

		return ResponseEntity.noContent().build();
	}

	@PostMapping("/loginId")
	public ResponseEntity<Void> checkLoginId(@RequestBody Map<String, String>  loginIdMap) {
		memberService.validateLoginId(loginIdMap.get("loginId"));

		return ResponseEntity.ok().build();
	}

	@PostMapping("/name")
	public ResponseEntity<Void> checkName(@RequestBody Map<String, String>  nameMap) {
		memberService.validateName(nameMap.get("name"));

		return ResponseEntity.ok().build();
	}

}
