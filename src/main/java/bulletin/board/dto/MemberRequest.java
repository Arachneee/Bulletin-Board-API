package bulletin.board.dto;

import org.hibernate.validator.constraints.Length;

import jakarta.validation.constraints.NotBlank;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MemberRequest {

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
