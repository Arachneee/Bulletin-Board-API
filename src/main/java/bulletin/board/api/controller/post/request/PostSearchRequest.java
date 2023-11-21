package bulletin.board.api.controller.post.request;

import bulletin.board.domain.post.constant.SearchCode;
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
