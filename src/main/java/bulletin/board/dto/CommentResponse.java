package bulletin.board.dto;

import java.time.LocalDateTime;

import bulletin.board.domain.Comment;
import bulletin.board.domain.Member;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CommentResponse {
	private Long id;
	private String content;
	private String name;
	private LocalDateTime createdDate;
	private Integer empathyCount;
	private boolean editButton;
	private boolean empathyButton;

	private CommentResponse(Long id, String content, String name, LocalDateTime createdDate, Integer empathyCount,
		boolean editButton, boolean empathyButton) {
		this.id = id;
		this.content = content;
		this.name = name;
		this.createdDate = createdDate;
		this.empathyCount = empathyCount;
		this.editButton = editButton;
		this.empathyButton = empathyButton;
	}

	public static CommentResponse of(Comment comment, Member member) {
		return new CommentResponse(comment.getId(),
			comment.getContent(),
			comment.getMember().getName(),
			comment.getCreatedDate(),
			comment.getEmpathyCount(),
			comment.isWriter(member),
			!(comment.isAlreadyEmpathized(member) || comment.isWriter(member)));
	}
}
