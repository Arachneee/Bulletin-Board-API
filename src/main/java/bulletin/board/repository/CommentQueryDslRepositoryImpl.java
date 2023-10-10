package bulletin.board.repository;

import static bulletin.board.domain.QComment.*;
import static bulletin.board.domain.QCommentEmpathy.*;
import static bulletin.board.domain.QMember.*;

import java.util.Optional;


import com.querydsl.jpa.impl.JPAQueryFactory;

import bulletin.board.domain.Comment;
import bulletin.board.domain.QCommentEmpathy;
import bulletin.board.domain.QMember;
import jakarta.persistence.EntityManager;

public class CommentQueryDslRepositoryImpl implements CommentQueryDslRepository {

	private final JPAQueryFactory queryFactory;

	public CommentQueryDslRepositoryImpl(EntityManager em) {
		this.queryFactory = new JPAQueryFactory(em);
	}

	@Override
	public Optional<Comment> findTopCommentByPostId(Long postId) {
		return Optional.ofNullable(queryFactory
							.selectFrom(comment)
							.join(comment.member, member).fetchJoin()
							.where(comment.post.id.eq(postId).and(comment.commentEmpathies.size().ne(0)))
							.orderBy(comment.commentEmpathies.size().desc())
							.limit(1)
							.fetchOne());

	}
}
