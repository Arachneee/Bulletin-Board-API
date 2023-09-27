package bulletin.board.core.common.exceptions;

public class AuthorityException extends BusinessException {
	public AuthorityException(ErrorCode errorCode) {
		super(errorCode);
	}
}
