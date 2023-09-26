package bulletin.board.exceptions;

import bulletin.board.web.error.ErrorCode;

public class DuplicatedLoginIdException extends BusinessException {

	public DuplicatedLoginIdException(ErrorCode errorCode) {
		super(errorCode);
	}
}
