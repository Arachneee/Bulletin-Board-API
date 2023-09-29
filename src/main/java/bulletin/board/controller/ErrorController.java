package bulletin.board.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import bulletin.board.exceptions.AuthorityException;
import bulletin.board.constant.ErrorCode;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
public class ErrorController {

	@RequestMapping("/error/login")
	public ResponseEntity<Void> loginError() {
		log.info("/error/login");
		throw new AuthorityException(ErrorCode.NEED_LOGIN);
	}

	@RequestMapping("/error/authority")
	public ResponseEntity<Void> authorityError() {
		log.info("/error/authority");
		throw new AuthorityException(ErrorCode.INVALID_AUTHORITY);
	}
}
