package bulletin.board.api.error;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.validation.BindingResult;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ErrorResponse {
	private String message;
	private String code;
	private List<BindingError> errors;

	private ErrorResponse(ErrorCode errorCode, List<BindingError> errors) {
		this.message = errorCode.getMessage();
		this.code = errorCode.getCode();
		this.errors = errors;
	}

	private ErrorResponse(ErrorCode errorCode) {
		this.code = errorCode.getCode();
		this.message = errorCode.getMessage();
		this.errors = new ArrayList<>();
	}

	public static ErrorResponse of(ErrorCode errorCode, BindingResult bindingResult) {
		return new ErrorResponse(errorCode, BindingError.of(bindingResult));
	}

	public static ErrorResponse of(ErrorCode errorCode) {
		return new ErrorResponse(errorCode);
	}

	@Getter
	@NoArgsConstructor(access = AccessLevel.PROTECTED)
	private static class BindingError {
		private String field;
		private String value;
		private String fieldMessage;

		public BindingError(String field, String value, String fieldMessage) {
			this.field = field;
			this.value = value;
			this.fieldMessage = fieldMessage;
		}

		public static List<BindingError> of(BindingResult bindingResult) {
			return bindingResult.getFieldErrors().stream()
										.map(fieldError -> new BindingError(
											fieldError.getField(),
											fieldError.getRejectedValue() == null ? "" :
												fieldError.getRejectedValue().toString(),
											fieldError.getDefaultMessage()))
										.collect(Collectors.toList());
		}
	}
}
