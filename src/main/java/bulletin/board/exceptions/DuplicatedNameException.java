package bulletin.board.exceptions;

import bulletin.board.web.error.ErrorCode;

public class DuplicatedNameException extends BusinessException {
	public DuplicatedNameException(ErrorCode errorCode) {
		super(errorCode);
	}
}
