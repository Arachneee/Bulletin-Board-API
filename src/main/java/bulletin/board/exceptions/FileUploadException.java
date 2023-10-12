package bulletin.board.exceptions;

import bulletin.board.constant.ErrorCode;
import lombok.Getter;

@Getter
public class FileUploadException extends RuntimeException {

    private final ErrorCode errorCode;

    public FileUploadException(ErrorCode errorCode) {
        super(errorCode.getMessage());
        this.errorCode = errorCode;
    }
}
