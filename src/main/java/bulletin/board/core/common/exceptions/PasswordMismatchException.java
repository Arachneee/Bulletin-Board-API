package bulletin.board.core.common.exceptions;

public class PasswordMismatchException extends BusinessException {
	public PasswordMismatchException(ErrorCode errorCode) {
		super(errorCode);
	}
}
