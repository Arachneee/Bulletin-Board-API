package bulletin.board.core.exceptions;

import bulletin.board.api.error.ErrorCode;

public class EmptyStringException extends BusinessException {
	public EmptyStringException(ErrorCode errorCode) {
		super(errorCode);
	}
}
