package bulletin.board.constant;

import static bulletin.board.domain.QPost.*;

import com.querydsl.core.types.dsl.StringPath;

import lombok.Getter;

@Getter
public enum SearchCode {
    TITLE(post.title), CONTENT(post.content), NAME(post.member.name);

    private final StringPath column;

    SearchCode(StringPath column) {
        this.column = column;
    }
}
