package bulletin.board.controller;

import java.net.URI;

import bulletin.board.dto.BestCommentResponse;
import bulletin.board.dto.CommentRequest;
import jakarta.validation.Valid;
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

import bulletin.board.argumentresolver.Login;
import bulletin.board.domain.Member;
import bulletin.board.dto.CommentResponse;
import bulletin.board.service.CommentService;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/posts/{postId}/comments")
public class CommentController {

	private final CommentService commentService;

	@PostMapping("")
	public ResponseEntity<Void> createComment(@Login Member member,
												@PathVariable Long postId,
												@Valid @RequestBody CommentRequest commentRequest) {
		Long commentId = commentService.createComment(member, postId, commentRequest);

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
		commentService.updateComment(commentId, commentRequest);

		return ResponseEntity.ok().build();
	}

	@DeleteMapping("/{commentId}")
	public ResponseEntity<Void> deleteComment(@PathVariable Long commentId) {
		commentService.deleteComment(commentId);

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
