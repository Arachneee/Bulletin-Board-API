package bulletin.board.exceptions;

import bulletin.board.web.error.ErrorCode;

public class AuthorityException extends BusinessException {
	public AuthorityException(ErrorCode errorCode) {
		super(errorCode);
	}
}
