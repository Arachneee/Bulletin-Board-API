package bulletin.board.web.controller.member;

import org.hibernate.validator.constraints.Length;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MemberCreateDto {

	@NotBlank
	@Length(max=30)
	private String loginId;
	@NotBlank
	@Length(max=30)
	private String password;
	@NotBlank
	@Length(max=30)
	private String passwordRe;
	@NotBlank
	@Length(max=30)
	private String name;
}
