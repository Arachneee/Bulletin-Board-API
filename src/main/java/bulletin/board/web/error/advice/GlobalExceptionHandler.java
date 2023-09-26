package bulletin.board.web.error.advice;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import bulletin.board.exceptions.BusinessException;
import bulletin.board.web.error.ErrorCode;
import bulletin.board.web.error.ErrorResponse;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

	@ExceptionHandler(MethodArgumentNotValidException.class)
	protected ResponseEntity<ErrorResponse> handleValidationException(MethodArgumentNotValidException e) {
		ErrorResponse errorResponse = ErrorResponse.of(ErrorCode.INVALID_INPUT, e.getBindingResult());
		return ResponseEntity.badRequest().body(errorResponse);
	}

	@ExceptionHandler(BusinessException.class)
	protected ResponseEntity<ErrorResponse> handleBusinessException(BusinessException e) {
		ErrorCode errorCode = e.getErrorCode();
		ErrorResponse errorResponse = ErrorResponse.of(errorCode);
		return ResponseEntity.status(errorCode.getStatus()).body(errorResponse);
	}
}
