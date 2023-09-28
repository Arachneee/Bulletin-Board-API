package bulletin.board.exceptions;

public class DuplicatedCommentEmpathyException extends BusinessException {
	public DuplicatedCommentEmpathyException(ErrorCode errorCode) {
		super(errorCode);
	}
}
