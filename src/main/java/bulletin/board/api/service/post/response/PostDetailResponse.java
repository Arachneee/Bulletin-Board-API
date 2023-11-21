package bulletin.board.api.service.post.response;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import bulletin.board.domain.member.Member;
import bulletin.board.domain.post.Post;
import bulletin.board.domain.post.UploadFile;
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
	private Integer empathyCount;
	private boolean editButton;
	private boolean empathyButton;
	private List<String> imageUrls;

	public static PostDetailResponse of(Post post, Member member) {
		return PostDetailResponse.builder()
									.id(post.getId())
									.title(post.getTitle())
									.content(post.getContent())
									.name(post.getWriterName())
									.createdDate(post.getCreatedDate())
									.viewCount(post.getViewCount())
									.empathyCount(post.getEmpathyCount())
									.editButton(post.isWriter(member))
									.empathyButton(post.canEmpathy(member))
									.imageUrls(post.getImages().stream()
										.map(UploadFile::getImageUrl)
										.collect(Collectors.toList()))
									.build();
	}
}
