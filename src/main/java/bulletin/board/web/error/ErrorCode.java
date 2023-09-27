package bulletin.board.web.error;

import lombok.Getter;

@Getter
public enum ErrorCode {

	DUPLICATED_LOGIN_ID(400, "ME_001", "이미 존재하는 ID 입니다."),
	DUPLICATED_NAME(400, "ME_002", "이미 존재하는 이름입니다."),
	PASSWORD_NOT_SAME(400, "ME_003", "비밀번호 확인이 다릅니다."),
	MEMBER_NOT_FOUND(400, "ME_004", "존재하지 않는 회원입니다."),
	INVALID_INPUT(400, "I_001", "적절하지 않은 입력입니다."),
	BAD_LOGIN(400, "AU_002", "잘못된 아이디 또는 패스워드입니다."),
	INVALID_AUTHORITY(400, "AU_001", "접근 권한이 없습니다."),
	POST_NOT_FOUND(400, "P_001", "존재하지 않는 게시글입니다.");

	private final String code;
	private final String message;
	private final int status;

	ErrorCode(int status, String code, String message) {
		this.status = status;
		this.message = message;
		this.code = code;
	}
}
