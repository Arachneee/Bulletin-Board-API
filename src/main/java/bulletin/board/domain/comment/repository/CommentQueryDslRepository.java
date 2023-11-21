package bulletin.board.domain.comment.repository;

import java.util.Optional;


import bulletin.board.domain.comment.Comment;

public interface CommentQueryDslRepository {
	Optional<Comment> findTopCommentByPostId(Long postId);
}
