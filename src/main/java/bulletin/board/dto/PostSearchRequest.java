package bulletin.board.dto;

import bulletin.board.constant.SearchCode;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PostSearchRequest {

    private SearchCode searchCode;

    private String searchString;

    public PostSearchRequest() {
        this.searchCode = SearchCode.TITLE;
        this.searchString = "";
    }
}
