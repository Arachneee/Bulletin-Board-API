package bulletin.board.web.controller.member;

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
import org.springframework.web.bind.annotation.SessionAttribute;

import bulletin.board.domain.Member;
import bulletin.board.dto.MemberRequest;
import bulletin.board.dto.MemberResponse;
import bulletin.board.service.MemberService;
import bulletin.board.web.session.SessionConst;
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
	public ResponseEntity<MemberResponse> findMember(@PathVariable("id") Long id) {
		return ResponseEntity.ok().body(memberService.findMember(id));
	}

	//Update
	@PatchMapping("/{id}")
	public ResponseEntity<Void> update(@SessionAttribute(name = SessionConst.LOGIN_MEMBER) Member member,
										@PathVariable("id") Long id,
										@RequestBody Map<String, String> nameMap) {
		memberService.update(member.getId(), id, nameMap.get("name"));
		return ResponseEntity.ok().build();
	}

	//Delete
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> delete(@SessionAttribute(name = SessionConst.LOGIN_MEMBER) Member member,
										@PathVariable("id") Long id) {
		memberService.delete(member.getId(), id);
		return ResponseEntity.noContent().build();
	}


}
