package bulletin.board.exceptions;

public class LoginFailException extends BusinessException {
	public LoginFailException(ErrorCode errorCode) {
		super(errorCode);
	}
}
