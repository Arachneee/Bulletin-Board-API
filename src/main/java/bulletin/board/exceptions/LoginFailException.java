package bulletin.board.exceptions;

import bulletin.board.exceptions.constant.ErrorCode;

public class LoginFailException extends AuthorityException {
	public LoginFailException(ErrorCode errorCode) {
		super(errorCode);
	}
}
