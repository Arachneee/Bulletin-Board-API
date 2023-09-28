package bulletin.board.core.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import bulletin.board.core.domain.Comment;
import bulletin.board.core.domain.Member;
import bulletin.board.core.domain.Post;
import bulletin.board.core.dto.CommentResponse;
import bulletin.board.core.common.exceptions.AuthorityException;
import bulletin.board.core.common.exceptions.EmptyStringException;
import bulletin.board.core.common.exceptions.EntityNotFoundException;
import bulletin.board.core.repository.CommentRepository;
import bulletin.board.core.repository.PostRepository;
import bulletin.board.core.common.exceptions.ErrorCode;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CommentService {

	private final CommentRepository commentRepository;
	private final PostRepository postRepository;

	@Transactional
	public Long save(Member member, Long postId, String commentContent) {
		Post findPost = postRepository.findById(postId)
			.orElseThrow(() -> new EntityNotFoundException(ErrorCode.POST_NOT_FOUND));

		validateString(commentContent);
		Comment comment = Comment.create(commentContent, findPost, member);
		commentRepository.save(comment);

		return comment.getId();
	}

	private void validateString(String commentContent) {
		if (!StringUtils.hasText(commentContent)) {
			throw new EmptyStringException(ErrorCode.INVALID_INPUT);
		}
	}

	public Page<CommentResponse> findComments(Long postId, Member member, Pageable pageable) {
		Page<Comment> comments = commentRepository.findWithMemberByPostId(postId, pageable);

		return comments.map(comment -> CommentResponse.of(comment, member));
	}

	@Transactional
	public void updateComment(Member member, Long commentId, String commentContent) {
		Comment findComment = commentRepository.findById(commentId)
			.orElseThrow(() -> new EntityNotFoundException(ErrorCode.COMMENT_NOT_FOUND));

		validateString(commentContent);

		findComment.update(commentContent);
	}

	@Transactional
	public void delete(Member member, Long commentId) {
		Comment findComment = commentRepository.findById(commentId)
			.orElseThrow(() -> new EntityNotFoundException(ErrorCode.COMMENT_NOT_FOUND));

		commentRepository.delete(findComment);
	}
}
