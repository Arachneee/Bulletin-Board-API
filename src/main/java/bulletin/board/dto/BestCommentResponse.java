package bulletin.board.dto;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import bulletin.board.domain.Comment;
import bulletin.board.domain.Member;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class BestCommentResponse {

	private Long id;
	private String content;
	private String name;
	private LocalDateTime createdDate;
	private Integer empathyCount;
	private boolean editButton;
	private boolean empathyButton;

	public static BestCommentResponse of(Comment comment, Member member) {
		return BestCommentResponse.builder()
			.id(comment.getId())
			.content(comment.getContent())
			.name(comment.getWriterName())
			.createdDate(comment.getCreatedDate())
			.empathyCount(comment.getEmpathyCount())
			.editButton(comment.isWriter(member))
			.empathyButton(comment.canEmpathy(member))
			.build();

	}
}
