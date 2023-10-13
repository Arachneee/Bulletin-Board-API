package bulletin.board.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Getter
@Setter
public class PostRequest {

	@NotBlank
	private String title;

	private List<MultipartFile> images;

	@NotBlank
	@Size(max = 10000)
	private String content;

	private List<Long> deleteImageIds;
}
