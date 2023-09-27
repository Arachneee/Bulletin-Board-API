package bulletin.board.core.exceptions;

import bulletin.board.api.error.ErrorCode;

public class LoginFailException extends BusinessException {
	public LoginFailException(ErrorCode errorCode) {
		super(errorCode);
	}
}
