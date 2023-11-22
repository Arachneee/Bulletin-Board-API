package bulletin.board.api.service.post.response;

import bulletin.board.domain.post.Post;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class PostResponse {

	private Long id;
	private String title;
	private String name;
	private LocalDateTime createdDate;
	private Integer viewCount;
	private Integer empathyCount;


	public static PostResponse from(final Post post) {
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
