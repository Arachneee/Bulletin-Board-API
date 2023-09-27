package bulletin.board.web.controller.post;

import lombok.Data;

@Data
public class PostSearchCondition {

	private String searchCode;
	private String searchString;
	private String sortCode;
	private Long page;

	public PostSearchCondition() {
		searchCode = "TITLE";
		searchString = "";
		sortCode = "NEW";
		page = 1L;
	}
}
