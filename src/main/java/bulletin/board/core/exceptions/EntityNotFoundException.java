package bulletin.board.core.exceptions;

import bulletin.board.api.error.ErrorCode;

public class EntityNotFoundException extends BusinessException {
	public EntityNotFoundException(ErrorCode errorCode) {
		super(errorCode);
	}
}
