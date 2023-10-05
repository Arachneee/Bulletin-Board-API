package bulletin.board.controller;

import java.net.URI;

import bulletin.board.dto.PostSearchRequest;
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
import bulletin.board.dto.PostDetailResponse;
import bulletin.board.dto.PostRequest;
import bulletin.board.dto.PostResponse;
import bulletin.board.service.PostService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/posts")
public class PostController {

	private final PostService postService;

	@PostMapping("")
	public ResponseEntity<Void> createPost(@Login Member member, @Valid @RequestBody PostRequest postRequest) {
		Long postId = postService.createPost(member, postRequest);

		return ResponseEntity.created(URI.create("/posts/" + postId)).build();
	}

	@GetMapping("")
	public ResponseEntity<Page<PostResponse>> findPosts(
		@Valid PostSearchRequest postSearchRequest,
		@Valid @PageableDefault(sort = "createdDate", direction = Sort.Direction.DESC) Pageable pageable) {

		log.info("vue 연결완료");
		return ResponseEntity.ok().body(postService.findPosts(postSearchRequest, pageable));
	}

	@GetMapping("/{id}")
	public ResponseEntity<PostDetailResponse> findPost(@Login Member member, @PathVariable Long id) {
		return ResponseEntity.ok().body(postService.findPost(member, id));
	}

	@PatchMapping("/{id}")
	public ResponseEntity<Void> updatePost(@PathVariable("id") Long id, @Valid @RequestBody PostRequest postRequest) {
		postService.updatePost(id, postRequest);

		return ResponseEntity.ok().build();
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deletePost(@PathVariable("id") Long id) {
		postService.deletePost(id);

		return ResponseEntity.noContent().build();
	}


}
