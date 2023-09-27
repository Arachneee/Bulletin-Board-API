package bulletin.board.api.comment;

import java.net.URI;
import java.util.Map;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
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

import bulletin.board.api.login.session.SessionConst;
import bulletin.board.core.domain.Member;
import bulletin.board.core.dto.CommentResponse;
import bulletin.board.core.service.CommentService;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/posts/{postId}/comments")
public class CommentController {

	private final CommentService commentService;

	@PostMapping("")
	public ResponseEntity<Void> createComment(@SessionAttribute(name = SessionConst.LOGIN_MEMBER) Member member,
												@PathVariable Long postId,
												@RequestBody Map<String, String> commentContentMap) {
		Long commentId = commentService.save(member, postId, commentContentMap.get("commentContent"));

		return ResponseEntity.created(URI.create("/posts/" + postId + "/comments" + commentId)).build();
	}

	@GetMapping("")
	public ResponseEntity<Page<CommentResponse>> findComments(@SessionAttribute(name = SessionConst.LOGIN_MEMBER) Member member,
																@PathVariable Long postId,
																@PageableDefault(sort = "createdDate", direction = Sort.Direction.DESC) Pageable pageable) {
		return ResponseEntity.ok().body(commentService.findComments(postId, member, pageable));
	}

	@PatchMapping("/{commentId}")
	public ResponseEntity<Void> updateComment(@SessionAttribute(name = SessionConst.LOGIN_MEMBER) Member member,
												@PathVariable Long commentId,
												@RequestBody Map<String, String> commentContentMap) {
		commentService.updateComment(member, commentId, commentContentMap.get("commentContent"));

		return ResponseEntity.ok().build();
	}

	@DeleteMapping("/{commentId}")
	public ResponseEntity<Void> deleteComment(@SessionAttribute(name = SessionConst.LOGIN_MEMBER) Member member,
												@PathVariable Long commentId) {
		commentService.delete(member, commentId);

		return ResponseEntity.noContent().build();
	}


}
