package bulletin.board.exceptions;

public class EntityNotFoundException extends BusinessException {
	public EntityNotFoundException(ErrorCode errorCode) {
		super(errorCode);
	}
}
