package bulletin.board.api;

import bulletin.board.exceptions.constant.ErrorCode;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

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

	private ErrorResponse(final ErrorCode errorCode) {
		this.code = errorCode.getCode();
		this.message = errorCode.getMessage();
		this.errors = new ArrayList<>();
	}

	private ErrorResponse(String message, String code) {
		this.message = message;
		this.code = code;
	}

	public static ErrorResponse of(final ErrorCode errorCode, final BindingResult bindingResult) {
		return new ErrorResponse(errorCode, BindingError.of(bindingResult));
	}

	public static ErrorResponse create(final String errorMessage, final String errorCode) {
		return new ErrorResponse(errorMessage, errorCode);
	}

	public static ErrorResponse from(final ErrorCode errorCode) {
		return new ErrorResponse(errorCode);
	}


	@Getter
	@Builder
	private static class BindingError {
		private String field;
		private String value;
		private String fieldMessage;

		public static List<BindingError> of(final BindingResult bindingResult) {
			return bindingResult.getFieldErrors()
				.stream()
				.map(BindingError::buildBindingError)
				.collect(Collectors.toList());
		}

		private static BindingError buildBindingError(final FieldError fieldError) {
			return BindingError.builder()
					.field(fieldError.getField())
					.value(getValue(fieldError))
					.fieldMessage(fieldError.getDefaultMessage())
					.build();
		}

		private static String getValue(final FieldError fieldError) {
			return fieldError.getRejectedValue() == null ? "" : fieldError.getRejectedValue().toString();
		}
	}
}
