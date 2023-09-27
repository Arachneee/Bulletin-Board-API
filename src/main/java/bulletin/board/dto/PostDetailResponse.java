package bulletin.board.dto;

import java.time.LocalDateTime;

import bulletin.board.domain.Post;
import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PostDetailResponse {

	private Long id;
	private String title;
	private String content;
	private String name;
	private LocalDateTime createdDate;
	private Integer viewCount;

	private PostDetailResponse(Long id, String title, String content, String name, LocalDateTime createdDate,
		Integer viewCount) {
		this.id = id;
		this.title = title;
		this.content = content;
		this.name = name;
		this.createdDate = createdDate;
		this.viewCount = viewCount;
	}

	public static PostDetailResponse of(Post post) {
		return new PostDetailResponse(post.getId(),
										post.getTitle(),
										post.getContent(),
										post.getMember().getName(),
										post.getCreatedDate(),
										post.getViewCount());
	}
}
