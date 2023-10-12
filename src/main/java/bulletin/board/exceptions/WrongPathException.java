package bulletin.board.exceptions;

import bulletin.board.constant.ErrorCode;
import lombok.Getter;

@Getter
public class WrongPathException extends RuntimeException {
    private final ErrorCode errorCode;

    public WrongPathException(ErrorCode errorCode) {
        super(errorCode.getMessage());
        this.errorCode = errorCode;
    }
}
