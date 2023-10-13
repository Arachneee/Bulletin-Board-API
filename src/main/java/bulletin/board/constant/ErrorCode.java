package bulletin.board.constant;

import lombok.Getter;

@Getter
public enum ErrorCode {

	INTERNAL_SERVER_ERROR(500, "S_001", "서버 오류입니다."),
	METHOD_NOT_ALLOWED(405, "M_001", "사용 불가능한 메소드입니다."),
	DUPLICATED_LOGIN_ID(400, "DU_001", "이미 존재하는 ID 입니다."),
	DUPLICATED_NAME(400, "DU_002", "이미 존재하는 이름입니다."),
	INVALID_AUTHORITY(403, "AU_001", "접근 권한이 없습니다."),
	NEED_LOGIN(403, "AU_002", "로그인이 필요합니다."),
	BAD_LOGIN(401, "AU_003", "잘못된 아이디 또는 패스워드입니다."),
	MEMBER_NOT_FOUND(404, "E_001", "존재하지 않는 회원입니다."),
	POST_NOT_FOUND(404, "E_002", "존재하지 않는 게시글입니다."),
	COMMENT_NOT_FOUND(404, "E_003", "존재하지 않는 댓글입니다."),
	EMPATHY_NOT_FOUND(404, "E_004", "존재하지 않는 공감입니다."),
	IMAGE_NOT_FOUND(404, "E_004", "존재하지 않는 이미지입니다."),
	INVALID_INPUT(400, "V_001", "적절하지 않은 입력입니다."),
	TYPE_MISMATCH(400, "V_002", "입력 타입이 잘못됐습니다."),
	NOT_JSON(400, "V_003", "JSON 타입에 맞지 않습니다."),
	DUPLICATED_EMPATHY(400, "EM_001", "이미 공감했습니다."),
	SELF_EMPATHY(400, "EM_001", "자신의 글에 공감할 수 없습니다."),
	FILE_NOT_UPLOAD(500, "F_001", "파일을 업로드 할 수 없습니다."),
	BAD_URL(500, "F_002", "파일 경로가 잘못되었습니다.");

	private final String code;
	private final String message;
	private final int status;

	ErrorCode(int status, String code, String message) {
		this.status = status;
		this.message = message;
		this.code = code;
	}
}
