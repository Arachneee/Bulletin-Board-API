package bulletin.board.exceptions;

import bulletin.board.exceptions.constant.ErrorCode;

public class DuplicatedNameException extends BusinessException {
	public DuplicatedNameException(ErrorCode errorCode) {
		super(errorCode);
	}
}
