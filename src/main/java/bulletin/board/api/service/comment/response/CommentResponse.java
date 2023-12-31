package bulletin.board.api.service.comment.response;

import bulletin.board.domain.comment.Comment;
import bulletin.board.domain.member.Member;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Builder
public class CommentResponse {
	private Long id;
	private String content;
	private String name;
	private LocalDateTime createdDate;
	private Integer empathyCount;
	private List<CommentResponse> replies;
	private boolean editButton;
	private boolean empathyButton;

	public static CommentResponse of(final Comment comment, final Member member) {
		return CommentResponse.builder()
				.id(comment.getId())
				.content(comment.getContent())
				.name(comment.getWriterName())
				.createdDate(comment.getCreatedDate())
				.empathyCount(comment.getEmpathyCount())
				.editButton(comment.isWriterOrAdmin(member))
				.empathyButton(comment.canEmpathy(member))
				.replies(comment.getReplies().stream()
						.map(replay -> CommentResponse.of(replay, member))
						.sorted(Comparator.comparing(CommentResponse::getCreatedDate).reversed())
						.collect(Collectors.toList()))
				.build();

	}
}
