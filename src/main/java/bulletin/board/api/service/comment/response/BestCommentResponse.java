package bulletin.board.api.service.comment.response;

import bulletin.board.domain.comment.Comment;
import bulletin.board.domain.member.Member;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

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

	public static BestCommentResponse of(final Comment comment, final Member member) {
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
