package bulletin.board.dto;

import bulletin.board.domain.Member;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class MemberResponse {

	private Long id;
	private String longId;
	private String name;

	public static MemberResponse of(Member member) {
		return MemberResponse.builder()
								.id(member.getId())
								.longId(member.getLoginId())
								.name(member.getName())
								.build();
	}
}
