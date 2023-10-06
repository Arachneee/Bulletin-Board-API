package bulletin.board.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

@Getter
@Setter
public class PostRequest {

	@NotBlank
	private String title;

	@NotBlank
	@Size(max = 10000)
	private String content;
}
