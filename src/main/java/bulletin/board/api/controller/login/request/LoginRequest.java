package bulletin.board.api.controller.login.request;

import jakarta.validation.constraints.NotBlank;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class LoginRequest {
	@NotBlank
	private String loginId;

	@NotBlank
	private String password;
}
