package bulletin.board.exceptions;

import bulletin.board.exceptions.constant.ErrorCode;

public class EntityNotFoundException extends BusinessException {
	public EntityNotFoundException(ErrorCode errorCode) {
		super(errorCode);
	}
}
