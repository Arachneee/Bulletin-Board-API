package bulletin.board.service;

import bulletin.board.domain.Member;
import bulletin.board.domain.Post;
import bulletin.board.dto.PostRequest;
import bulletin.board.dto.PostDetailResponse;
import bulletin.board.dto.PostResponse;
import bulletin.board.exceptions.EntityNotFoundException;
import bulletin.board.exceptions.PostSearchCodeException;
import bulletin.board.repository.PostRepository;
import bulletin.board.exceptions.ErrorCode;
import lombok.RequiredArgsConstructor;

import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Slf4j
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
		log.info("before find post");
		Post findPost = postRepository.findById(postId)
			.orElseThrow(() -> new EntityNotFoundException(ErrorCode.POST_NOT_FOUND));
		log.info("after find post");

		findPost.update(postRequest.getTitle(), postRequest.getContent());
		log.info("after update post");
	}

	@Transactional
	public PostDetailResponse findPost(Member member, Long id) {
		Post post = postRepository.findWithMemberById(id)
			.orElseThrow(() -> new EntityNotFoundException(ErrorCode.POST_NOT_FOUND));

		post.view();

		return PostDetailResponse.of(post, member);
	}

	@Transactional(readOnly = true)
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
	public void deletePost(Long postId) {
		Post findPost = postRepository.findWithCommentsById(postId)
			.orElseThrow(() -> new EntityNotFoundException(ErrorCode.POST_NOT_FOUND));

		postRepository.delete(findPost);
	}

}
