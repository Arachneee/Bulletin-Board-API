package bulletin.board.core.service;

import bulletin.board.core.domain.Member;
import bulletin.board.core.domain.Post;
import bulletin.board.core.dto.PostRequest;
import bulletin.board.core.dto.PostDetailResponse;
import bulletin.board.core.dto.PostResponse;
import bulletin.board.core.common.exceptions.AuthorityException;
import bulletin.board.core.common.exceptions.EntityNotFoundException;
import bulletin.board.core.common.exceptions.PostSearchCodeException;
import bulletin.board.core.repository.PostRepository;
import bulletin.board.core.common.exceptions.ErrorCode;
import lombok.RequiredArgsConstructor;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PostService {

	private final PostRepository postRepository;

	@Transactional
	public Long save(Member member, PostRequest postRequest) {
		Post savedPost = postRepository.save(Post.create(postRequest.getTitle(), postRequest.getContent(), member));
		return savedPost.getId();
	}

	@Transactional
	public void updatePost(Member member, Long postId, PostRequest postRequest) {
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

	public Page<PostResponse> findPosts(String searchCode, String searchString, Pageable pageable) {
		Page<Post> posts = switch (searchCode) {
			case "TITLE" -> postRepository.findByTitleContains(searchString, pageable);
			case "CONTENT" -> postRepository.findByContentContains(searchString, pageable);
			case "NAME" -> postRepository.findByNameContains(searchString, pageable);
			default -> throw new PostSearchCodeException(ErrorCode.INVALID_INPUT);
		};

		return posts.map(PostResponse::of);
	}

	@Transactional
	public void delete(Member member, Long postId) {
		Post findPost = postRepository.findById(postId)
			.orElseThrow(() -> new EntityNotFoundException(ErrorCode.POST_NOT_FOUND));

		postRepository.delete(findPost);
	}

}
