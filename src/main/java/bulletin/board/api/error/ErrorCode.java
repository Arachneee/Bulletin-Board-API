package bulletin.board.api.error;

import lombok.Getter;

@Getter
public enum ErrorCode {

	DUPLICATED_LOGIN_ID(400, "DU_001", "이미 존재하는 ID 입니다."),
	DUPLICATED_NAME(400, "DU_002", "이미 존재하는 이름입니다."),
	PASSWORD_NOT_SAME(400, "PA_001", "비밀번호 확인이 다릅니다."),
	MEMBER_NOT_FOUND(404, "E_001", "존재하지 않는 회원입니다."),
	INVALID_INPUT(400, "V_001", "적절하지 않은 입력입니다."),
	BAD_LOGIN(401, "AU_002", "잘못된 아이디 또는 패스워드입니다."),
	INVALID_AUTHORITY(403, "AU_001", "접근 권한이 없습니다."),
	POST_NOT_FOUND(404, "E_002", "존재하지 않는 게시글입니다."),
	COMMENT_NOT_FOUND(404, "E_003", "존재하지 않는 댓글입니다."),
	METHOD_NOT_ALLOWED(405, "M_001", "사용 불가능한 메소드입니다.");

	private final String code;
	private final String message;
	private final int status;

	ErrorCode(int status, String code, String message) {
		this.status = status;
		this.message = message;
		this.code = code;
	}
}
