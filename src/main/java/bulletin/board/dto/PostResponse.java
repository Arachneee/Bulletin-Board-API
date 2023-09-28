package bulletin.board.dto;

import java.time.LocalDateTime;

import bulletin.board.domain.Post;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PostResponse {

	private Long id;
	private String title;
	private String name;
	private LocalDateTime createdDate;
	private Integer viewCount;

	private PostResponse(Long id, String title, String name, LocalDateTime createdDate,
		Integer viewCount) {
		this.id = id;
		this.title = title;
		this.name = name;
		this.createdDate = createdDate;
		this.viewCount = viewCount;
	}

	public static PostResponse of(Post post) {
		return new PostResponse(post.getId(),
			post.getTitle(),
			post.getMember().getName(),
			post.getCreatedDate(),
			post.getViewCount());
	}

}
