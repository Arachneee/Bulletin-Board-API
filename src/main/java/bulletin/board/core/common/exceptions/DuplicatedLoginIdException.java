package bulletin.board.core.common.exceptions;

public class DuplicatedLoginIdException extends BusinessException {

	public DuplicatedLoginIdException(ErrorCode errorCode) {
		super(errorCode);
	}
}
