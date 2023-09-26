package bulletin.board.exceptions;

import bulletin.board.web.error.ErrorCode;

public class LoginFailException extends BusinessException {
	public LoginFailException(ErrorCode errorCode) {
		super(errorCode);
	}
}
