package bulletin.board.exceptions;

import bulletin.board.web.error.ErrorCode;

public class PasswordMismatchException extends BusinessException {
	public PasswordMismatchException(ErrorCode errorCode) {
		super(errorCode);
	}
}
