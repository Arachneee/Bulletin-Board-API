package bulletin.board.web.controller.post;

import java.net.URI;
import java.util.List;

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
import bulletin.board.dto.PostDetailResponse;
import bulletin.board.dto.PostRequest;
import bulletin.board.dto.PostResponse;
import bulletin.board.service.PostService;
import bulletin.board.web.session.SessionConst;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/posts")
public class PostController {

	private final PostService postService;

	@PostMapping("")
	public ResponseEntity<Void> createPost(@SessionAttribute(name = SessionConst.LOGIN_MEMBER) Member member,
											@RequestBody @Valid PostRequest postRequest) {
		Long postId = postService.save(member, postRequest);
		return ResponseEntity.created(URI.create("/posts/" + postId)).build();
	}

	@GetMapping("")
	public ResponseEntity<List<PostResponse>> findPosts(@RequestBody PostSearchCondition postSearchCondition) {
		return ResponseEntity.ok().body(postService.findPosts(postSearchCondition));
	}

	@GetMapping("/{id}")
	public ResponseEntity<PostDetailResponse> findPost(@PathVariable Long id) {
		return ResponseEntity.ok().body(postService.findPost(id));
	}

	@PatchMapping("/{id}")
	public ResponseEntity<Void> updatePost(@SessionAttribute(name = SessionConst.LOGIN_MEMBER) Member member,
											@PathVariable("id") Long id,
											@RequestBody @Valid PostRequest postRequest) {
		postService.updatePost(member, id, postRequest);
		return ResponseEntity.ok().build();
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deletePost(@SessionAttribute(name = SessionConst.LOGIN_MEMBER) Member member,
											@PathVariable("id") Long id) {
		postService.delete(member, id);
		return ResponseEntity.noContent().build();
	}


}
