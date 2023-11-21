package bulletin.board.domain.post.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import bulletin.board.domain.post.constant.SearchCode;
import bulletin.board.domain.post.Post;

public interface PostQueryDslRepository {

	Page<Post> searchPosts(SearchCode searchCode, String searchString, Pageable pageable);
}
