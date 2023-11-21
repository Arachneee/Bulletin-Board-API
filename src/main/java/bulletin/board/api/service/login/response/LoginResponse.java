package bulletin.board.api.service.login.response;

import bulletin.board.api.service.member.response.MemberResponse;
import bulletin.board.domain.member.Member;
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
    private static final String LOGIN_MESSAGE = "로그인 성공했습니다.";

    public LoginResponse(String message, MemberResponse memberResponse) {
        this.message = message;
        this.memberResponse = memberResponse;
    }

    public static LoginResponse login(Member member) {
        return new LoginResponse(LOGIN_MESSAGE, MemberResponse.of(member));
    }
}
