package bulletin.board.exceptions;

import bulletin.board.web.error.ErrorCode;

public class EntityNotFoundException extends BusinessException {
	public EntityNotFoundException(ErrorCode errorCode) {
		super(errorCode);
	}
}
