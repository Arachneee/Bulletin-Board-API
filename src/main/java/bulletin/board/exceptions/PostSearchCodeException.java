package bulletin.board.exceptions;

import bulletin.board.exceptions.constant.ErrorCode;

public class PostSearchCodeException extends BusinessException {
	public PostSearchCodeException(ErrorCode errorCode) {
		super(errorCode);
	}
}
