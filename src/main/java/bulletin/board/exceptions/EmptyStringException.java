package bulletin.board.exceptions;

import bulletin.board.constant.ErrorCode;

public class EmptyStringException extends BusinessException {
	public EmptyStringException(ErrorCode errorCode) {
		super(errorCode);
	}
}
