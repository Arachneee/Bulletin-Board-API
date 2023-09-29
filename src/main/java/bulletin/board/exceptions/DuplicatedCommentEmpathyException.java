package bulletin.board.exceptions;

import bulletin.board.constant.ErrorCode;

public class DuplicatedCommentEmpathyException extends BusinessException {
	public DuplicatedCommentEmpathyException(ErrorCode errorCode) {
		super(errorCode);
	}
}
