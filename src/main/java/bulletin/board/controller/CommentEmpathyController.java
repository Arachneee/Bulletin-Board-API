package bulletin.board.controller;

import bulletin.board.argumentresolver.Login;
import bulletin.board.domain.Member;
import bulletin.board.service.CommentEmpathyService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/posts/{postId}/comments/{commentId}/empathy")
public class CommentEmpathyController {

	private final CommentEmpathyService commentEmpathyService;

	@PostMapping("")
	public ResponseEntity<Void> createCommentEmpathy(@Login Member member,
														@PathVariable Long commentId) {
		commentEmpathyService.createCommentEmpathy(member, commentId);

		return ResponseEntity.accepted().build();
	}

	@DeleteMapping("")
	public ResponseEntity<Void> deleteCommentEmpathy(@Login Member member, @PathVariable Long commentId) {
		commentEmpathyService.deleteCommentEmpathy(member, commentId);

		return ResponseEntity.noContent().build();
	}

}
