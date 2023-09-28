package bulletin.board.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import bulletin.board.domain.Comment;
import bulletin.board.domain.Member;
import bulletin.board.domain.Post;
import bulletin.board.dto.CommentResponse;
import bulletin.board.exceptions.EmptyStringException;
import bulletin.board.exceptions.EntityNotFoundException;
import bulletin.board.repository.CommentRepository;
import bulletin.board.repository.PostRepository;
import bulletin.board.exceptions.ErrorCode;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CommentService {

	private final CommentRepository commentRepository;
	private final PostRepository postRepository;

	@Transactional
	public Long createComment(Member member, Long postId, String commentContent) {
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

	public CommentResponse findComment(Member member, Long commentId) {
		Comment findComment = commentRepository.findWithMemberAndEmpathyById(commentId)
			.orElseThrow(() -> new EntityNotFoundException(ErrorCode.COMMENT_NOT_FOUND));

		return CommentResponse.of(findComment, member);
	}

	public CommentResponse findBestComment(Member member, Long postId) {
		Comment findComment = commentRepository.findTop1WithMemberAndEmpathyByPostId(postId)
			.orElseThrow(() -> new EntityNotFoundException(ErrorCode.COMMENT_NOT_FOUND));

		return CommentResponse.of(findComment, member);
	}

	@Transactional
	public void updateComment(Long commentId, String commentContent) {
		Comment findComment = commentRepository.findById(commentId)
			.orElseThrow(() -> new EntityNotFoundException(ErrorCode.COMMENT_NOT_FOUND));

		validateString(commentContent);

		findComment.update(commentContent);
	}

	@Transactional
	public void deleteComment(Long commentId) {
		Comment findComment = commentRepository.findById(commentId)
			.orElseThrow(() -> new EntityNotFoundException(ErrorCode.COMMENT_NOT_FOUND));

		commentRepository.delete(findComment);
	}


}
