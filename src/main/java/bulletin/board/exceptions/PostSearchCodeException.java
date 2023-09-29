package bulletin.board.exceptions;

import bulletin.board.constant.ErrorCode;

public class PostSearchCodeException extends BusinessException {
	public PostSearchCodeException(ErrorCode errorCode) {
		super(errorCode);
	}
}
