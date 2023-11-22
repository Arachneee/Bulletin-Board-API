package bulletin.board.domain.post.repository;

import bulletin.board.domain.post.Post;
import bulletin.board.domain.post.QPost;
import bulletin.board.domain.post.constant.SearchCode;
import com.querydsl.core.types.Order;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.dsl.PathBuilder;
import com.querydsl.core.types.dsl.StringPath;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.support.PageableExecutionUtils;

import java.util.List;
import java.util.stream.Collectors;

import static bulletin.board.domain.member.QMember.member;
import static bulletin.board.domain.post.QPost.post;

@Slf4j
public class PostQueryDslRepositoryImpl implements PostQueryDslRepository {

	private final JPAQueryFactory queryFactory;

	public PostQueryDslRepositoryImpl(EntityManager em) {
		this.queryFactory = new JPAQueryFactory(em);
	}

	@Override
	public Page<Post> searchPosts(final SearchCode searchCode, final String searchString, final Pageable pageable) {
		final QPost postSub = new QPost("postSub");
		final StringPath searchColumn = searchCode.getColumn();

		final List<Post> contents = getPosts(searchString, pageable, searchColumn);
		final JPAQuery<Post> countQuery = getCountQuery(searchString, searchColumn);

		return PageableExecutionUtils.getPage(contents,	pageable, countQuery::fetchCount);
	}

	private List<Post> getPosts(final String searchString, final Pageable pageable, final StringPath searchColumn) {
		return queryFactory
				.selectFrom(post)
				.where(searchColumn.contains(searchString))
				.leftJoin(post.member, member).fetchJoin()
				.orderBy(getAllOrderSpecifiers(pageable).toArray(OrderSpecifier[]::new))
				.offset(pageable.getOffset())
				.limit(pageable.getPageSize())
				.fetch();
	}

	private JPAQuery<Post> getCountQuery(final String searchString, final StringPath searchColumn) {
		return queryFactory
				.selectFrom(post)
				.where(searchColumn.contains(searchString));
	}

	private List<OrderSpecifier> getAllOrderSpecifiers(final Pageable pageable) {
		return pageable.getSort().stream()
						.map(PostQueryDslRepositoryImpl::getOrderSpecifier)
						.collect(Collectors.toList());
	}

	private static OrderSpecifier getOrderSpecifier(final Sort.Order order) {
		log.info("ORDER : {}, {}",getDirection(order), getPathBuilder(order));

		return new OrderSpecifier(getDirection(order), getPathBuilder(order));
	}

	private static PathBuilder<Object> getPathBuilder(final Sort.Order order) {
		return new PathBuilder<>(post.getType(), post.getMetadata()).get(order.getProperty());
	}

	private static Order getDirection(final Sort.Order order) {
		return order.getDirection().isAscending() ? Order.ASC : Order.DESC;
	}
}
