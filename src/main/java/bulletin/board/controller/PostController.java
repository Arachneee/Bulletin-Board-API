package bulletin.board.controller;

import java.net.URI;

import bulletin.board.dto.*;
import bulletin.board.service.UploadFileService;

import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ContentDisposition;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import bulletin.board.argumentresolver.Login;
import bulletin.board.domain.Member;
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
	private final UploadFileService uploadFileService;

	@PostMapping("")
	public ResponseEntity<Void> createPost(@Login Member member, PostRequest postRequest) {
		Long postId = postService.createPost(member, postRequest);

		return ResponseEntity.created(URI.create("/posts/" + postId)).build();
	}

	@GetMapping("")
	public ResponseEntity<Page<PostResponse>> findPosts(
		@Valid PostSearchRequest postSearchRequest,
		@Valid @PageableDefault(sort = "createdDate", direction = Sort.Direction.DESC) Pageable pageable) {

		return ResponseEntity.ok().body(postService.findPosts(postSearchRequest, pageable));
	}

	@GetMapping("/{id}")
	public ResponseEntity<PostDetailResponse> findPost(@Login Member member, @PathVariable Long id) {
		PostDetailResponse post = postService.findPost(member, id);

		return ResponseEntity.ok().body(post);
	}

	@PatchMapping("/{id}")
	public ResponseEntity<Void> updatePost(@PathVariable("id") Long id, @Valid PostRequest postRequest) {
		postService.updatePost(id, postRequest);

		return ResponseEntity.ok().build();
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deletePost(@PathVariable("id") Long id) {
		postService.deletePost(id);

		return ResponseEntity.noContent().build();
	}

	@GetMapping("/{id}/images/{imageId}")
	public ResponseEntity<Resource> findImage(@PathVariable Long imageId) {

		ByteArrayResource resource = uploadFileService.findImage(imageId);

		return ResponseEntity.ok()
				.contentType(MediaType.APPLICATION_OCTET_STREAM)
				.contentLength(resource.contentLength())
				.header(HttpHeaders.CONTENT_DISPOSITION,
						ContentDisposition.attachment()
								.filename(imageId.toString() + ".jpg")
								.build().toString())
				.body(resource);
	}

	@DeleteMapping("/{id}/images/{imageId}")
	public ResponseEntity<Void> deleteImage(@PathVariable Long imageId) {

		uploadFileService.delete(imageId);

		return ResponseEntity.noContent().build();
	}
}
