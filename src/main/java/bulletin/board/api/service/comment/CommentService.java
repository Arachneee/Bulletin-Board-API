package bulletin.board.api.service.comment;


import bulletin.board.api.service.comment.response.BestCommentResponse;
import bulletin.board.api.controller.comment.request.CommentRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import bulletin.board.domain.comment.Comment;
import bulletin.board.domain.member.Member;
import bulletin.board.domain.post.Post;
import bulletin.board.api.service.comment.response.CommentResponse;
import bulletin.board.exceptions.EntityNotFoundException;
import bulletin.board.domain.comment.repository.CommentRepository;
import bulletin.board.domain.post.repository.PostRepository;
import bulletin.board.exceptions.constant.ErrorCode;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CommentService {

	private final CommentRepository commentRepository;
	private final PostRepository postRepository;

	@Transactional
	public Long createComment(Member member, Long postId, CommentRequest commentRequest) {
		Post findPost = postRepository.findById(postId)
			.orElseThrow(() -> new EntityNotFoundException(ErrorCode.POST_NOT_FOUND));

		Comment comment = Comment.create(commentRequest.getCommentContent(), findPost, member);
		commentRepository.save(comment);

		return comment.getId();
	}

	@Transactional(readOnly = true)
	public Page<CommentResponse> findComments(Long postId, Member member, Pageable pageable) {
		Page<Comment> comments = commentRepository.findWithMemberByPostIdAndParentCommentIsNull(postId, pageable);

		return comments.map(comment -> CommentResponse.of(comment, member));
	}

	@Transactional(readOnly = true)
	public CommentResponse findComment(Member member, Long commentId) {
		Comment findComment = commentRepository.findWithMemberAndEmpathyById(commentId)
			.orElseThrow(() -> new EntityNotFoundException(ErrorCode.COMMENT_NOT_FOUND));

		return CommentResponse.of(findComment, member);
	}

	@Transactional(readOnly = true)
	public BestCommentResponse findBestComment(Member member, Long postId) {
		Comment findComment = commentRepository.findTopCommentByPostId(postId)
			.orElseThrow(() -> new EntityNotFoundException(ErrorCode.COMMENT_NOT_FOUND));

		return BestCommentResponse.of(findComment, member);
	}

	@Transactional
	public void updateComment(Long commentId, CommentRequest commentRequest) {
		Comment findComment = commentRepository.findById(commentId)
			.orElseThrow(() -> new EntityNotFoundException(ErrorCode.COMMENT_NOT_FOUND));

		findComment.update(commentRequest.getCommentContent());
	}

	@Transactional
	public void deleteComment(Long commentId) {
		Comment findComment = commentRepository.findWithEmpathyById(commentId)
			.orElseThrow(() -> new EntityNotFoundException(ErrorCode.COMMENT_NOT_FOUND));

		commentRepository.delete(findComment);
	}


	@Transactional
	public Long createReply(Member member, Long commentId, CommentRequest commentRequest) {
		Comment findComment = commentRepository.findWithPostById(commentId)
				.orElseThrow(() -> new EntityNotFoundException(ErrorCode.COMMENT_NOT_FOUND));

		Comment replyComment = Comment.createReply(commentRequest.getCommentContent(), findComment.getRootComment(), member);
		commentRepository.save(replyComment);

		return replyComment.getId();
	}
}
