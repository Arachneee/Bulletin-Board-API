package bulletin.board.service;

import bulletin.board.domain.Member;
import bulletin.board.domain.Post;
import bulletin.board.dto.PostRequest;
import bulletin.board.dto.PostDetailResponse;
import bulletin.board.dto.PostResponse;
import bulletin.board.dto.PostSearchRequest;
import bulletin.board.exceptions.EntityNotFoundException;
import bulletin.board.repository.PostRepository;
import bulletin.board.constant.ErrorCode;
import lombok.RequiredArgsConstructor;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@RequiredArgsConstructor
public class PostService {

	private final PostRepository postRepository;

	@Transactional
	public Long createPost(Member member, PostRequest postRequest) {
		Post savedPost = postRepository.save(Post.create(postRequest.getTitle(), postRequest.getContent(), member));
		return savedPost.getId();
	}

	@Transactional
	public void updatePost(Long postId, PostRequest postRequest) {
		Post findPost = postRepository.findById(postId)
			.orElseThrow(() -> new EntityNotFoundException(ErrorCode.POST_NOT_FOUND));

		findPost.update(postRequest.getTitle(), postRequest.getContent());
	}

	@Transactional
	public PostDetailResponse findPost(Member member, Long id) {
		Post post = postRepository.findWithMemberById(id)
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

		postRepository.delete(findPost);
	}

}
