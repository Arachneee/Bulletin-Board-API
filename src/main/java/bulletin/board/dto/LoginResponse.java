package bulletin.board.dto;

import bulletin.board.domain.Member;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class LoginResponse {

    private String message;
    private MemberResponse memberResponse;

    public LoginResponse(String message, MemberResponse memberResponse) {
        this.message = message;
        this.memberResponse = memberResponse;
    }

    public static LoginResponse login(Member member) {
        return new LoginResponse("로그인 성공했습니다.", MemberResponse.of(member));
    }
}
