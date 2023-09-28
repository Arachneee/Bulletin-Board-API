package bulletin.board.exceptions;

public class DuplicatedNameException extends BusinessException {
	public DuplicatedNameException(ErrorCode errorCode) {
		super(errorCode);
	}
}
