package bulletin.board.controller;

import java.net.URI;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
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

	@PostMapping("")
	public ResponseEntity<Void> createMember(@RequestBody @Valid MemberRequest memberRequest) {
		Long saveId = memberService.createMember(memberRequest);

		return ResponseEntity.created(URI.create("/members/" + saveId)).build();
	}

	@GetMapping("/{id}")
	public ResponseEntity<MemberResponse> findMember(@PathVariable("id") Long id) {
		return ResponseEntity.ok().body(memberService.findMember(id));
	}

	@PatchMapping("/{id}")
	public ResponseEntity<Void> updateName(@PathVariable("id") Long id, @RequestBody Map<String, String> nameMap) {
		memberService.updateName(id, nameMap.get("name"));

		return ResponseEntity.ok().build();
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deleteMember(@PathVariable("id") Long id) {
		memberService.deleteMember(id);

		return ResponseEntity.noContent().build();
	}

}
