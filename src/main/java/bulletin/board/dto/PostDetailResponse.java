package bulletin.board.dto;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import bulletin.board.domain.Member;
import bulletin.board.domain.Post;
import bulletin.board.domain.UploadFile;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class PostDetailResponse {

	private Long id;
	private String title;
	private String content;
	private String name;
	private LocalDateTime createdDate;
	private Integer viewCount;
	private boolean editButton;
	private List<Long> imageIds;

	public static PostDetailResponse of(Post post, Member member) {
		return PostDetailResponse.builder()
									.id(post.getId())
									.title(post.getTitle())
									.content(post.getContent())
									.name(post.getWriterName())
									.createdDate(post.getCreatedDate())
									.viewCount(post.getViewCount())
									.editButton(post.isWriter(member))
									.imageIds(post.getImages().stream()
										.map(UploadFile::getId)
										.collect(Collectors.toList()))
									.build();
	}
}
