package bulletin.board.core.dto;

import java.time.LocalDateTime;

import bulletin.board.core.domain.Member;
import bulletin.board.core.domain.Post;
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
	private boolean editButton;

	private PostDetailResponse(Long id, String title, String content, String name, LocalDateTime createdDate,
		Integer viewCount, boolean editButton) {
		this.id = id;
		this.title = title;
		this.content = content;
		this.name = name;
		this.createdDate = createdDate;
		this.viewCount = viewCount;
		this.editButton = editButton;
	}

	public static PostDetailResponse of(Post post, Member member) {
		return new PostDetailResponse(post.getId(),
										post.getTitle(),
										post.getContent(),
										post.getMember().getName(),
										post.getCreatedDate(),
										post.getViewCount(),
										post.getMember().getId().equals(member.getId()));
	}
}
