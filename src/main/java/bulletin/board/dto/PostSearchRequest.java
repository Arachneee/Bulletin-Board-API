package bulletin.board.dto;

import bulletin.board.constant.SearchCode;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PostSearchRequest {

    private SearchCode searchCode = SearchCode.TITLE;

    private String searchString = "";
}
