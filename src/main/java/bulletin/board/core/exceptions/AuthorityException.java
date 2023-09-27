package bulletin.board.core.exceptions;

import bulletin.board.api.error.ErrorCode;

public class AuthorityException extends BusinessException {
	public AuthorityException(ErrorCode errorCode) {
		super(errorCode);
	}
}
