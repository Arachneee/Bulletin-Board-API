package bulletin.board.repository;

import java.util.Optional;


import bulletin.board.domain.Comment;

public interface CommentQueryDslRepository {
	Optional<Comment> findTopCommentByPostId(Long postId);
}
