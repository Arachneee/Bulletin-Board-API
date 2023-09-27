package bulletin.board.core.common.exceptions;

public class DuplicatedNameException extends BusinessException {
	public DuplicatedNameException(ErrorCode errorCode) {
		super(errorCode);
	}
}
