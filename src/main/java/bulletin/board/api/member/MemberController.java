package bulletin.board.api.member;

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

import bulletin.board.api.login.argumentresolver.Login;
import bulletin.board.core.domain.Member;
import bulletin.board.core.dto.MemberRequest;
import bulletin.board.core.dto.MemberResponse;
import bulletin.board.core.service.MemberService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/members")
public class MemberController {
	private final MemberService memberService;

	@PostMapping("")
	public ResponseEntity<Void> createMember(@RequestBody @Valid MemberRequest memberRequest) {
		Long saveId = memberService.save(memberRequest);
		return ResponseEntity.created(URI.create("/members/" + saveId)).build();
	}

	@GetMapping("/{id}")
	public ResponseEntity<MemberResponse> findMember(@PathVariable("id") Long id) {
		return ResponseEntity.ok().body(memberService.findMember(id));
	}

	@PatchMapping("/{id}")
	public ResponseEntity<Void> updateName(@Login Member member,
										@PathVariable("id") Long id,
										@RequestBody Map<String, String> nameMap) {
		memberService.updateName(member, id, nameMap.get("name"));
		return ResponseEntity.ok().build();
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deleteMember(@Login Member member,
										@PathVariable("id") Long id) {
		memberService.delete(member, id);
		return ResponseEntity.noContent().build();
	}

}
