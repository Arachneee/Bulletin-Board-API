package bulletin.board.core.exceptions;

import bulletin.board.api.error.ErrorCode;

public class PostSearchCodeException extends BusinessException {
	public PostSearchCodeException(ErrorCode errorCode) {
		super(errorCode);
	}
}
