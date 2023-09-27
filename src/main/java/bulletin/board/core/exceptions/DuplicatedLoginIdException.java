package bulletin.board.core.exceptions;

import bulletin.board.api.error.ErrorCode;

public class DuplicatedLoginIdException extends BusinessException {

	public DuplicatedLoginIdException(ErrorCode errorCode) {
		super(errorCode);
	}
}
