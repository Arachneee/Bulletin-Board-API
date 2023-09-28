package bulletin.board.exceptions;

public class PostSearchCodeException extends BusinessException {
	public PostSearchCodeException(ErrorCode errorCode) {
		super(errorCode);
	}
}
