package bulletin.board.exceptions;

public class LoginFailException extends AuthorityException {
	public LoginFailException(ErrorCode errorCode) {
		super(errorCode);
	}
}
