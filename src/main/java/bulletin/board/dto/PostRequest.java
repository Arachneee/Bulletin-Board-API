package bulletin.board.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Getter
@Setter
public class PostRequest {

	@NotBlank
	private String title;

	@NotBlank
	private String content;
}
