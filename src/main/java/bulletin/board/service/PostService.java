package bulletin.board.service;

import bulletin.board.domain.Member;
import bulletin.board.domain.Post;
import bulletin.board.domain.UploadFile;
import bulletin.board.dto.PostRequest;
import bulletin.board.dto.PostDetailResponse;
import bulletin.board.dto.PostResponse;
import bulletin.board.dto.PostSearchRequest;
import bulletin.board.exceptions.EntityNotFoundException;
import bulletin.board.repository.PostRepository;
import bulletin.board.constant.ErrorCode;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;

import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.stream.Collectors;


@Slf4j
@Service
@RequiredArgsConstructor
public class PostService {

	private final PostRepository postRepository;
	private final EntityManager em;
	private final UploadFileService uploadFileService;

	@Transactional
	public Long createPost(Member member, PostRequest postRequest) {

		Post savedPost = postRepository.save(Post.create(postRequest.getTitle(), postRequest.getContent(), member));

		uploadFileService.storeFiles(postRequest.getImages(), savedPost);

		return savedPost.getId();
	}

	@Transactional
	public void updatePost(Long postId, PostRequest postRequest) {

		Post findPost = postRepository.findById(postId)
			.orElseThrow(() -> new EntityNotFoundException(ErrorCode.POST_NOT_FOUND));

		findPost.update(postRequest.getTitle(), postRequest.getContent());

		uploadFileService.deleteFiles(postRequest.getDeleteImageIds());

		uploadFileService.updateFiles(postRequest.getImages(), findPost);
	}

	@Transactional
	public PostDetailResponse findPost(Member member, Long id) {
		Post post = postRepository.findWithMemberAndImagesById(id)
			.orElseThrow(() -> new EntityNotFoundException(ErrorCode.POST_NOT_FOUND));

		post.view();

		return PostDetailResponse.of(post, member);
	}

	@Transactional(readOnly = true)
	public Page<PostResponse> findPosts(PostSearchRequest postSearchRequest, Pageable pageable) {
		Page<Post> posts = postRepository.searchPosts(postSearchRequest.getSearchCode(),
														postSearchRequest.getSearchString(),
														pageable);

		return posts.map(PostResponse::of);
	}

	@Transactional
	public void deletePost(Long postId) {
		Post findPost = postRepository.findWithCommentsById(postId)
			.orElseThrow(() -> new EntityNotFoundException(ErrorCode.POST_NOT_FOUND));

		uploadFileService.deleteFiles(findPost.getImages().stream()
												.map(UploadFile::getId)
												.collect(Collectors.toList()));

		postRepository.delete(findPost);
	}

}
