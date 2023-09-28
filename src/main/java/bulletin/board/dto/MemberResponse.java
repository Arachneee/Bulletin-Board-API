package bulletin.board.dto;

import bulletin.board.domain.Member;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MemberResponse {

	private Long id;
	private String longId;
	private String name;

	private MemberResponse(Long id, String longId, String name) {
		this.id = id;
		this.longId = longId;
		this.name = name;
	}

	public static MemberResponse of(Member member) {
		return new MemberResponse(member.getId(), member.getLoginId(), member.getName());
	}
}
