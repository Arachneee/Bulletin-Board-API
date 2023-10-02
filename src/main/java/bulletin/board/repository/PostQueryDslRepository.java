package bulletin.board.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import bulletin.board.constant.SearchCode;
import bulletin.board.domain.Post;

public interface PostQueryDslRepository {

	Page<Post> searchPosts(SearchCode searchCode, String searchString, Pageable pageable);
}
