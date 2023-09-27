package bulletin.board.api.post;

import java.net.URI;

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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.SessionAttribute;

import bulletin.board.core.domain.Member;
import bulletin.board.core.dto.PostDetailResponse;
import bulletin.board.core.dto.PostRequest;
import bulletin.board.core.dto.PostResponse;
import bulletin.board.core.service.PostService;
import bulletin.board.api.session.SessionConst;
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
	public ResponseEntity<Page<PostResponse>> findPosts(@RequestParam(value = "searchCode", defaultValue = "TITLE") String searchCode,
															@RequestParam(value = "searchString", defaultValue = "") String searchString,
															@PageableDefault(sort = "createdDate", direction = Sort.Direction.DESC) Pageable pageable) {
		return ResponseEntity.ok().body(postService.findPosts(searchCode, searchString, pageable));
	}

	@GetMapping("/{id}")
	public ResponseEntity<PostDetailResponse> findPost(@SessionAttribute(name = SessionConst.LOGIN_MEMBER) Member member,
														@PathVariable Long id) {
		return ResponseEntity.ok().body(postService.findPost(member, id));
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
