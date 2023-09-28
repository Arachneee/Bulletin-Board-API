package bulletin.board.exceptions;

public class EmptyStringException extends BusinessException {
	public EmptyStringException(ErrorCode errorCode) {
		super(errorCode);
	}
}
