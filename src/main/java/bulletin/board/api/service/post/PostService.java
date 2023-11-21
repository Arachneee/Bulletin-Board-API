package bulletin.board.api.service.post;

import bulletin.board.domain.member.Member;
import bulletin.board.domain.post.Post;
import bulletin.board.domain.post.UploadFile;
import bulletin.board.api.controller.post.request.PostRequest;
import bulletin.board.api.service.post.response.PostDetailResponse;
import bulletin.board.api.service.post.response.PostResponse;
import bulletin.board.api.controller.post.request.PostSearchRequest;
import bulletin.board.exceptions.EntityNotFoundException;
import bulletin.board.domain.post.repository.PostRepository;
import bulletin.board.exceptions.constant.ErrorCode;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;

import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
