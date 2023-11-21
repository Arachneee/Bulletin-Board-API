package bulletin.board.exceptions;

import bulletin.board.exceptions.constant.ErrorCode;
import lombok.Getter;

@Getter
public class AuthorityException extends RuntimeException {
	private final ErrorCode errorCode;

	public AuthorityException(ErrorCode errorCode) {
		super(errorCode.getMessage());
		this.errorCode = errorCode;
	}
}
