package bulletin.board.exceptions;

import bulletin.board.web.error.ErrorCode;

public class EmptyStringException extends BusinessException {
	public EmptyStringException(ErrorCode errorCode) {
		super(errorCode);
	}
}
