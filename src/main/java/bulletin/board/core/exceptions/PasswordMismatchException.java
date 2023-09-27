package bulletin.board.core.exceptions;

import bulletin.board.api.error.ErrorCode;

public class PasswordMismatchException extends BusinessException {
	public PasswordMismatchException(ErrorCode errorCode) {
		super(errorCode);
	}
}
