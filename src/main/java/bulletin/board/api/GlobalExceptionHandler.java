package bulletin.board.api;

import bulletin.board.exceptions.BusinessException;
import bulletin.board.exceptions.constant.ErrorCode;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.net.URI;
import java.nio.file.AccessDeniedException;

@RestControllerAdvice
public class GlobalExceptionHandler {

	@Order(1)
	@ExceptionHandler(HttpRequestMethodNotSupportedException.class)
	protected ResponseEntity<ErrorResponse> handleHttpRequestMethodNotSupportedException(HttpRequestMethodNotSupportedException e) {
		ErrorResponse errorResponse = ErrorResponse.from(ErrorCode.METHOD_NOT_ALLOWED);
		return ResponseEntity.status(HttpStatus.METHOD_NOT_ALLOWED).body(errorResponse);
	}

	@Order(3)
	@ExceptionHandler(AccessDeniedException.class)
	protected ResponseEntity<ErrorResponse> handleAuthorityException(AccessDeniedException e) {
		ErrorResponse errorResponse = ErrorResponse.from(ErrorCode.INVALID_AUTHORITY);
		return ResponseEntity.status(HttpStatus.FORBIDDEN).location(URI.create("/login")).body(errorResponse);
	}

	@Order(4)
	@ExceptionHandler(HttpMessageNotReadableException.class)
	protected ResponseEntity<ErrorResponse> handleMethodArgumentTypeMismatchException(HttpMessageNotReadableException e) {
		ErrorResponse errorResponse = ErrorResponse.from(ErrorCode.NOT_JSON);
		return ResponseEntity.badRequest().body(errorResponse);
	}

	@Order(5)
	@ExceptionHandler(MethodArgumentTypeMismatchException.class)
	protected ResponseEntity<ErrorResponse> handleMethodArgumentTypeMismatchException(MethodArgumentTypeMismatchException e) {
		ErrorResponse errorResponse = ErrorResponse.from(ErrorCode.TYPE_MISMATCH);
		return ResponseEntity.badRequest().body(errorResponse);
	}

	@Order(6)
	@ExceptionHandler(MethodArgumentNotValidException.class)
	protected ResponseEntity<ErrorResponse> handleValidationException(MethodArgumentNotValidException e) {
		ErrorResponse errorResponse = ErrorResponse.of(ErrorCode.INVALID_INPUT, e.getBindingResult());
		return ResponseEntity.badRequest().body(errorResponse);
	}

	@Order(7)
	@ExceptionHandler(BusinessException.class)
	protected ResponseEntity<ErrorResponse> handleBusinessException(BusinessException e) {
		ErrorCode errorCode = e.getErrorCode();
		ErrorResponse errorResponse = ErrorResponse.from(errorCode);
		return ResponseEntity.status(errorCode.getStatus()).body(errorResponse);
	}

	@Order(8)
	@ExceptionHandler(Exception.class)
	protected ResponseEntity<ErrorResponse> handleException(Exception e) {
		ErrorResponse errorResponse = ErrorResponse.from(ErrorCode.INTERNAL_SERVER_ERROR);
		return ResponseEntity.internalServerError().body(errorResponse);
	}
}
