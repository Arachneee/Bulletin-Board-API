package bulletin.board.exceptions;

import bulletin.board.constant.ErrorCode;

public class DuplicatedLoginIdException extends BusinessException {

	public DuplicatedLoginIdException(ErrorCode errorCode) {
		super(errorCode);
	}
}
