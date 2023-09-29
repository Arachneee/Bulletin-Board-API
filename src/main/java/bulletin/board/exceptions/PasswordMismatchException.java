package bulletin.board.exceptions;

import bulletin.board.constant.ErrorCode;

public class PasswordMismatchException extends BusinessException {
	public PasswordMismatchException(ErrorCode errorCode) {
		super(errorCode);
	}
}
