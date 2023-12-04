package bulletin.board.api.service.post;

import bulletin.board.api.controller.post.request.PostRequest;
import bulletin.board.api.controller.post.request.PostSearchRequest;
import bulletin.board.api.service.post.response.PostDetailResponse;
import bulletin.board.api.service.post.response.PostResponse;
import bulletin.board.domain.member.Member;
import bulletin.board.domain.post.Post;
import bulletin.board.domain.post.repository.PostRepository;
import bulletin.board.exceptions.EntityNotFoundException;
import bulletin.board.exceptions.constant.ErrorCode;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PostService {

	private final PostRepository postRepository;
	private final EntityManager em;
	private final UploadFileService uploadFileService;

	@Transactional
	public Long create(final Member member, final PostRequest postRequest) {
		Post savedPost = postRepository.save(createPost(member, postRequest));

		uploadFileService.storeFiles(postRequest.getImages(), savedPost);

		return savedPost.getId();
	}

	private static Post createPost(final Member member, final PostRequest postRequest) {
		return Post.create(postRequest.getTitle(), postRequest.getContent(), member);
	}

	@PreAuthorize("@postChecker.isSelf(#postId) or hasAuthority('ADMIN')")
	@Transactional
	public void update(final Long postId, final PostRequest postRequest) {
		Post findPost = postRepository.findById(postId)
			.orElseThrow(() -> new EntityNotFoundException(ErrorCode.POST_NOT_FOUND));

		findPost.update(postRequest.getTitle(), postRequest.getContent());

		uploadFileService.deleteFiles(postRequest.getDeleteImageIds());
		uploadFileService.updateFiles(postRequest.getImages(), findPost);
	}

	@Transactional
	public PostDetailResponse findPost(final Member member, final Long id) {
		Post post = postRepository.findWithMemberAndImagesById(id)
			.orElseThrow(() -> new EntityNotFoundException(ErrorCode.POST_NOT_FOUND));

		post.view();

		return PostDetailResponse.of(post, member);
	}

	public Page<PostResponse> findPosts(final PostSearchRequest postSearchRequest, final Pageable pageable) {
		Page<Post> posts = postRepository.searchPosts(postSearchRequest.getSearchCode(),
														postSearchRequest.getSearchString(),
														pageable);

		return posts.map(PostResponse::from);
	}

	@PreAuthorize("@postChecker.isSelf(#postId) or hasAuthority('ADMIN')")
	@Transactional
	public void delete(final Long postId) {
		Post findPost = postRepository.findWithCommentsById(postId)
			.orElseThrow(() -> new EntityNotFoundException(ErrorCode.POST_NOT_FOUND));

		uploadFileService.deleteFiles(findPost.getImageIds());

		postRepository.delete(findPost);
	}

}
