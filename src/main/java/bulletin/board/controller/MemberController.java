package bulletin.board.controller;

import java.net.URI;
import java.util.Map;

import bulletin.board.dto.MemberNameRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

	@PostMapping("")
	public ResponseEntity<Void> createMember(@Valid @RequestBody MemberRequest memberRequest) {
		Long saveId = memberService.createMember(memberRequest);

		return ResponseEntity.created(URI.create("/members/" + saveId)).build();
	}

	@GetMapping("/{id}")
	public ResponseEntity<MemberResponse> findMember(@PathVariable("id") Long id) {
		return ResponseEntity.ok().body(memberService.findMember(id));
	}

	@PatchMapping("/{id}")
	public ResponseEntity<Void> updateName(@PathVariable("id") Long id, @Valid @RequestBody MemberNameRequest memberNameRequest) {
		memberService.updateName(id, memberNameRequest);

		return ResponseEntity.ok().build();
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deleteMember(@PathVariable("id") Long id) {
		memberService.deleteMember(id);

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
