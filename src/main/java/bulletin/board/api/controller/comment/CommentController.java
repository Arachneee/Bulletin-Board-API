package bulletin.board.api.controller.comment;

import bulletin.board.api.controller.comment.request.CommentRequest;
import bulletin.board.api.service.comment.CommentService;
import bulletin.board.api.service.comment.response.BestCommentResponse;
import bulletin.board.api.service.comment.response.CommentResponse;
import bulletin.board.api.service.login.Login;
import bulletin.board.domain.member.Member;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/posts/{postId}/comments")
public class CommentController {

	private final CommentService commentService;

	@PostMapping("")
	public ResponseEntity<Void> createComment(@Login Member member,
												@PathVariable Long postId,
												@Valid @RequestBody CommentRequest commentRequest) {
		Long commentId = commentService.create(member, postId, commentRequest);
		return ResponseEntity.created(URI.create("/posts/" + postId + "/comments/" + commentId)).build();
	}

	@GetMapping("")
	public ResponseEntity<Page<CommentResponse>> findComments(
						@Login Member member,
						@PathVariable Long postId,
						@PageableDefault(sort = "createdDate", direction = Sort.Direction.DESC) Pageable pageable) {
		return ResponseEntity.ok().body(commentService.findComments(postId, member, pageable));
	}

	@GetMapping("/top")
	public ResponseEntity<BestCommentResponse> findBestComment(@Login Member member, @PathVariable Long postId) {
		return ResponseEntity.ok().body(commentService.findBestComment(member, postId));
	}

	@GetMapping("/{commentId}")
	public ResponseEntity<CommentResponse> findComment(@Login Member member, @PathVariable Long commentId) {
		return ResponseEntity.ok().body(commentService.findComment(member, commentId));
	}

	@PatchMapping("/{commentId}")
	public ResponseEntity<Void> updateComment(@PathVariable Long commentId,
											  @Valid @RequestBody CommentRequest commentRequest) {
		commentService.update(commentId, commentRequest);
		return ResponseEntity.ok().build();
	}

	@DeleteMapping("/{commentId}")
	public ResponseEntity<Void> deleteComment(@PathVariable Long commentId) {
		commentService.delete(commentId);
		return ResponseEntity.noContent().build();
	}

	@PostMapping("/{commentId}")
	public ResponseEntity<Void> createReply(@Login Member member,
											@PathVariable Long postId,
											@PathVariable Long commentId,
											@Valid @RequestBody CommentRequest commentRequest) {
		Long replyCommentId = commentService.createReply(member, commentId, commentRequest);
		return ResponseEntity.created(URI.create("/posts/" + postId + "/comments/" + commentId + "/replies/" + replyCommentId)).build();
	}

}
