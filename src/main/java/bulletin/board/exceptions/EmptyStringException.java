package bulletin.board.exceptions;

import bulletin.board.exceptions.constant.ErrorCode;

public class EmptyStringException extends BusinessException {
	public EmptyStringException(ErrorCode errorCode) {
		super(errorCode);
	}
}
