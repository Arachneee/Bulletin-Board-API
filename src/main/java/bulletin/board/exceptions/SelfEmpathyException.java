package bulletin.board.exceptions;

import bulletin.board.exceptions.constant.ErrorCode;

public class SelfEmpathyException extends BusinessException {
	public SelfEmpathyException(ErrorCode errorCode) {
		super(errorCode);
	}
}
