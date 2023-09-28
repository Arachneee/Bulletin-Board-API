package bulletin.board.advice;

import java.net.URI;

import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import bulletin.board.dto.ErrorResponse;
import bulletin.board.exceptions.AuthorityException;
import bulletin.board.exceptions.BusinessException;
import bulletin.board.exceptions.ErrorCode;
import bulletin.board.exceptions.LoginFailException;

@RestControllerAdvice
public class GlobalExceptionHandler {

	@Order(1)
	@ExceptionHandler(HttpRequestMethodNotSupportedException.class)
	protected ResponseEntity<ErrorResponse> handleHttpRequestMethodNotSupportedException(HttpRequestMethodNotSupportedException e) {
		ErrorResponse errorResponse = ErrorResponse.of(ErrorCode.METHOD_NOT_ALLOWED);
		return ResponseEntity.status(HttpStatus.METHOD_NOT_ALLOWED).body(errorResponse);
	}

	@Order(2)
	@ExceptionHandler(LoginFailException.class)
	protected ResponseEntity<ErrorResponse> handleAuthorityException(LoginFailException e) {
		ErrorCode errorCode = e.getErrorCode();
		ErrorResponse errorResponse = ErrorResponse.of(errorCode);
		return ResponseEntity.status(errorCode.getStatus()).location(URI.create("/members")).body(errorResponse);
	}

	@Order(3)
	@ExceptionHandler(AuthorityException.class)
	protected ResponseEntity<ErrorResponse> handleAuthorityException(AuthorityException e) {
		ErrorCode errorCode = e.getErrorCode();
		ErrorResponse errorResponse = ErrorResponse.of(errorCode);
		return ResponseEntity.status(errorCode.getStatus()).location(URI.create("/login")).body(errorResponse);
	}

	@Order(4)
	@ExceptionHandler(HttpMessageNotReadableException.class)
	protected ResponseEntity<ErrorResponse> handleMethodArgumentTypeMismatchException(HttpMessageNotReadableException e) {
		ErrorResponse errorResponse = ErrorResponse.of(ErrorCode.NOT_JSON);
		return ResponseEntity.badRequest().body(errorResponse);
	}

	@Order(4)
	@ExceptionHandler(MethodArgumentTypeMismatchException.class)
	protected ResponseEntity<ErrorResponse> handleMethodArgumentTypeMismatchException(MethodArgumentTypeMismatchException e) {
		ErrorResponse errorResponse = ErrorResponse.of(ErrorCode.TYPE_MISMATCH);
		return ResponseEntity.badRequest().body(errorResponse);
	}

	@Order(5)
	@ExceptionHandler(MethodArgumentNotValidException.class)
	protected ResponseEntity<ErrorResponse> handleValidationException(MethodArgumentNotValidException e) {
		ErrorResponse errorResponse = ErrorResponse.of(ErrorCode.INVALID_INPUT, e.getBindingResult());
		return ResponseEntity.badRequest().body(errorResponse);
	}

	@Order(6)
	@ExceptionHandler(BusinessException.class)
	protected ResponseEntity<ErrorResponse> handleBusinessException(BusinessException e) {
		ErrorCode errorCode = e.getErrorCode();
		ErrorResponse errorResponse = ErrorResponse.of(errorCode);
		return ResponseEntity.status(errorCode.getStatus()).body(errorResponse);
	}

	@Order(7)
	@ExceptionHandler(Exception.class)
	protected ResponseEntity<ErrorResponse> handleException(Exception e) {
		ErrorResponse errorResponse = ErrorResponse.of(ErrorCode.INTERNAL_SERVER_ERROR);
		return ResponseEntity.internalServerError().body(errorResponse);
	}
}
