package bulletin.board.web.controller.login;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginDto {
	@NotBlank
	private String loginId;

	@NotBlank
	private String password;
}
