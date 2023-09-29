package bulletin.board.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import bulletin.board.domain.Comment;
import bulletin.board.domain.CommentEmpathy;
import bulletin.board.domain.Member;
import bulletin.board.exceptions.DuplicatedCommentEmpathyException;
import bulletin.board.exceptions.EntityNotFoundException;
import bulletin.board.constant.ErrorCode;
import bulletin.board.exceptions.SelfEmpathyException;
import bulletin.board.repository.CommentEmpathyRepository;
import bulletin.board.repository.CommentRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CommentEmpathyService {

	private final CommentEmpathyRepository commentEmpathyRepository;
	private final CommentRepository commentRepository;

	@Transactional
	public Long createCommentEmpathy(Member member, Long commentId) {
		Comment findComment = commentRepository.findWithEmpathyById(commentId)
			.orElseThrow(() -> new EntityNotFoundException(ErrorCode.COMMENT_NOT_FOUND));

		validateEmpathy(member, findComment);

		CommentEmpathy commentEmpathy = CommentEmpathy.create(findComment, member);
		commentEmpathyRepository.save(commentEmpathy);

		return commentEmpathy.getId();
	}

	private void validateEmpathy(Member member, Comment comment) {
		if (comment.isAlreadyEmpathized(member)) {
			throw new DuplicatedCommentEmpathyException(ErrorCode.DUPLICATED_EMPATHY);
		}

		if (comment.isWriter(member)) {
			throw new SelfEmpathyException(ErrorCode.SELF_EMPATHY);
		}
	}

	@Transactional
	public void deleteCommentEmpathy(Member member, Long commentId) {
		CommentEmpathy commentEmpathy = commentEmpathyRepository.findByMemberAndCommentId(member, commentId)
			.orElseThrow(() -> new EntityNotFoundException(ErrorCode.EMPATHY_NOT_FOUND));

		commentEmpathyRepository.delete(commentEmpathy);
	}
}
