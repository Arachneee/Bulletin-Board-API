package bulletin.board.exceptions;

import bulletin.board.exceptions.constant.ErrorCode;

public class DuplicatedLoginIdException extends BusinessException {

	public DuplicatedLoginIdException(ErrorCode errorCode) {
		super(errorCode);
	}
}
