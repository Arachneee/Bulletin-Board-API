package bulletin.board.core.common.exceptions;

public class LoginFailException extends BusinessException {
	public LoginFailException(ErrorCode errorCode) {
		super(errorCode);
	}
}
