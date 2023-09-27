package bulletin.board.core.exceptions;

import bulletin.board.api.error.ErrorCode;

public class DuplicatedNameException extends BusinessException {
	public DuplicatedNameException(ErrorCode errorCode) {
		super(errorCode);
	}
}
