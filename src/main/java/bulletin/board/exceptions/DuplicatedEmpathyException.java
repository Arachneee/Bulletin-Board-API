package bulletin.board.exceptions;

import bulletin.board.constant.ErrorCode;

public class DuplicatedEmpathyException extends BusinessException {
	public DuplicatedEmpathyException(ErrorCode errorCode) {
		super(errorCode);
	}
}
