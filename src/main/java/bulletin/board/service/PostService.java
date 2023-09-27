package bulletin.board.service;

import java.util.List;

import bulletin.board.domain.Member;
import bulletin.board.domain.Post;
import bulletin.board.dto.PostDetailResponse;
import bulletin.board.dto.PostRequest;
import bulletin.board.dto.PostResponse;
import bulletin.board.exceptions.AuthorityException;
import bulletin.board.exceptions.EntityNotFoundException;
import bulletin.board.repository.PostRepository;
import bulletin.board.web.controller.post.PostSearchCondition;
import bulletin.board.web.error.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PostService {

	private final PostRepository postRepository;
	private final Long PAGE_SIZE = 10L;
	private final Integer COMMENT_PAGE_SIZE = 10;

	@Transactional
	public Long save(Member member, PostRequest postRequest) {
		Post savedPost = postRepository.save(Post.create(postRequest.getTitle(), postRequest.getContent(), member));
		return savedPost.getId();
	}

	@Transactional
	public void updatePost(Member member, Long postId, PostRequest postRequest) {
		Post findPost = postRepository.findById(postId)
			.orElseThrow(() -> new EntityNotFoundException(ErrorCode.POST_NOT_FOUND));

		validatePostAuthority(member, findPost);

		findPost.update(postRequest.getTitle(), postRequest.getContent());
	}

	public PostDetailResponse findPost(Long id) {
		Post post = postRepository.findById(id)
			.orElseThrow(() -> new EntityNotFoundException(ErrorCode.POST_NOT_FOUND));

		return PostDetailResponse.of(post);
	}

	public List<PostResponse> findPosts(PostSearchCondition postSearchCondition) {

	}

	@Transactional
	public void delete(Member member, Long postId) {
		Post findPost = postRepository.findById(postId)
			.orElseThrow(() -> new EntityNotFoundException(ErrorCode.POST_NOT_FOUND));

		validatePostAuthority(member, findPost);

		postRepository.delete(findPost);
	}

	private static void validatePostAuthority(Member member, Post findPost) {
		if (!findPost.getMember().getId().equals(member.getId())) {
			throw new AuthorityException(ErrorCode.INVALID_AUTHORITY);
		}
	}


}
