package bulletin.board.exceptions;

import bulletin.board.exceptions.constant.ErrorCode;

public class DuplicatedEmpathyException extends BusinessException {
	public DuplicatedEmpathyException(ErrorCode errorCode) {
		super(errorCode);
	}
}
