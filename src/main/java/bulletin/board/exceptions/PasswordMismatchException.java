package bulletin.board.exceptions;

public class PasswordMismatchException extends BusinessException {
	public PasswordMismatchException(ErrorCode errorCode) {
		super(errorCode);
	}
}
