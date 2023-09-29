package bulletin.board.exceptions;

import bulletin.board.constant.ErrorCode;

public class SelfEmpathyException extends BusinessException {
	public SelfEmpathyException(ErrorCode errorCode) {
		super(errorCode);
	}
}
