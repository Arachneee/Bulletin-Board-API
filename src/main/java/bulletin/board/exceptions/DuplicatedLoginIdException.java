package bulletin.board.exceptions;

public class DuplicatedLoginIdException extends BusinessException {

	public DuplicatedLoginIdException(ErrorCode errorCode) {
		super(errorCode);
	}
}
