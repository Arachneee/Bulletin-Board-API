package bulletin.board.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import bulletin.board.domain.Comment;
import bulletin.board.domain.CommentEmpathy;
import bulletin.board.domain.Member;
import bulletin.board.exceptions.DuplicatedCommentEmpathyException;
import bulletin.board.exceptions.EntityNotFoundException;
import bulletin.board.exceptions.ErrorCode;
import bulletin.board.exceptions.SelfEmpathyException;
import bulletin.board.repository.CommentEmpathyRepository;
import bulletin.board.repository.CommentRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
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

	private static void validateEmpathy(Member member, Comment findComment) {
		if (findComment.isAlreadyEmpathized(member)) {
			throw new DuplicatedCommentEmpathyException(ErrorCode.DUPLICATED_EMPATHY);
		}

		if (findComment.isWriter(member)) {
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
