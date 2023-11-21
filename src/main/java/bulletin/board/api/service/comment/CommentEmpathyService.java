package bulletin.board.api.service.comment;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import bulletin.board.domain.comment.Comment;
import bulletin.board.domain.comment.CommentEmpathy;
import bulletin.board.domain.member.Member;
import bulletin.board.exceptions.EntityNotFoundException;
import bulletin.board.exceptions.constant.ErrorCode;
import bulletin.board.domain.comment.repository.CommentEmpathyRepository;
import bulletin.board.domain.comment.repository.CommentRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CommentEmpathyService {

	private final CommentEmpathyRepository commentEmpathyRepository;
	private final CommentRepository commentRepository;

	@Transactional
	public void createCommentEmpathy(Member member, Long commentId) {
		Comment findComment = commentRepository.findWithEmpathyById(commentId)
			.orElseThrow(() -> new EntityNotFoundException(ErrorCode.COMMENT_NOT_FOUND));

		CommentEmpathy commentEmpathy = CommentEmpathy.create(findComment, member);
		commentEmpathyRepository.save(commentEmpathy);
	}

	@Transactional
	public void deleteCommentEmpathy(Member member, Long commentId) {
		CommentEmpathy commentEmpathy = commentEmpathyRepository.findByMemberAndCommentId(member, commentId)
			.orElseThrow(() -> new EntityNotFoundException(ErrorCode.EMPATHY_NOT_FOUND));

		commentEmpathyRepository.delete(commentEmpathy);
	}
}
