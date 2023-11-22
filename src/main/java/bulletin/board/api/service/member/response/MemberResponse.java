package bulletin.board.api.service.member.response;

import bulletin.board.domain.member.Member;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class MemberResponse {

	private Long id;
	private String longId;
	private String name;

	public static MemberResponse from(final Member member) {
		return MemberResponse.builder()
								.id(member.getId())
								.longId(member.getLoginId())
								.name(member.getName())
								.build();
	}
}
