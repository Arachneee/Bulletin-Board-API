package bulletin.board.repository;

import static bulletin.board.domain.QMember.*;
import static bulletin.board.domain.QPost.*;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.support.PageableExecutionUtils;

import com.querydsl.core.types.Order;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.dsl.PathBuilder;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;

import bulletin.board.constant.SearchCode;
import bulletin.board.domain.Post;
import jakarta.persistence.EntityManager;

public class PostQueryDslRepositoryImpl implements PostQueryDslRepository {

	private final JPAQueryFactory queryFactory;

	public PostQueryDslRepositoryImpl(EntityManager em) {
		this.queryFactory = new JPAQueryFactory(em);
	}

	@Override
	public Page<Post> searchPosts(SearchCode searchCode, String searchString, Pageable pageable) {
		List<Post> content = queryFactory
			.selectFrom(post)
			.where(searchCode.getColumn().contains(searchString))
			.leftJoin(post.member, member).fetchJoin()
			.orderBy(getAllOrderSpecifiers(pageable).toArray(OrderSpecifier[]::new))
			.offset(pageable.getOffset())
			.limit(pageable.getPageSize())
			.fetch();

		JPAQuery<Post> countQuery = queryFactory
			.selectFrom(post)
			.where(searchCode.getColumn().contains(searchString));

		return PageableExecutionUtils.getPage(content, pageable, countQuery::fetchCount);

	}

	private List<OrderSpecifier> getAllOrderSpecifiers(Pageable pageable) {
		return pageable.getSort().stream()
						.map(PostQueryDslRepositoryImpl::getOrderSpecifier)
						.collect(Collectors.toList());
	}

	private static OrderSpecifier getOrderSpecifier(Sort.Order order) {
		return new OrderSpecifier(getDirection(order), getPathBuilder(order));
	}

	private static PathBuilder<Object> getPathBuilder(Sort.Order order) {
		return new PathBuilder<>(post.getType(), post.getMetadata()).get(order.getProperty());
	}

	private static Order getDirection(Sort.Order order) {
		return order.getDirection().isAscending() ? Order.ASC : Order.DESC;
	}
}
