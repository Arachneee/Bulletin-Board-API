package bulletin.board.dto;

import java.time.LocalDateTime;

import bulletin.board.domain.Post;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class PostResponse {

	private Long id;
	private String title;
	private String name;
	private LocalDateTime createdDate;
	private Integer viewCount;
	private Integer empathyCount;


	public static PostResponse of(Post post) {
		return PostResponse.builder()
							.id(post.getId())
							.title(post.getTitle())
							.name(post.getWriterName())
							.createdDate(post.getCreatedDate())
							.viewCount(post.getViewCount())
							.empathyCount(post.getEmpathyCount())
							.build();

	}

}
