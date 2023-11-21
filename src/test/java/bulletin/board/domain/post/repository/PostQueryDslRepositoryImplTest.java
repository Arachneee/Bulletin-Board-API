package bulletin.board.domain.post.repository;

import static org.assertj.core.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.transaction.annotation.Transactional;

import bulletin.board.IntegrationTestSupport;
import bulletin.board.domain.member.Member;
import bulletin.board.domain.member.MemberRepository;
import bulletin.board.domain.post.Post;
import bulletin.board.domain.post.constant.SearchCode;

@Transactional
class PostQueryDslRepositoryImplTest extends IntegrationTestSupport {

	@Autowired
	PostQueryDslRepositoryImpl postQueryDslRepository;

	@Autowired
	PostRepository postRepository;

	@Autowired
	MemberRepository memberRepository;

	@DisplayName("제목, 검색어, 페이징으로 게시글을 찾을 수 있다.")
	@Test
	void searchPostsTitle() {
		// given
		Member member1 = Member.create("aaa", "1234", "AAA");
		Member member2 = Member.create("bbb", "1234", "BBB");
		memberRepository.saveAll(List.of(member1, member2));

		Post post1 = Post.create("제목11", "내용11", member1);
		Post post2 = Post.create("제목12", "내용12", member1);
		Post post3 = Post.create("제목23", "내용23", member2);
		Post post4 = Post.create("제목24", "내용34", member2);
		postRepository.saveAll(List.of(post1, post2, post3, post4));

		SearchCode searchCode = SearchCode.TITLE;
		String searchString = "2";
		Sort sort = Sort.by("createdDate").descending();
		Pageable pageable = PageRequest.of(0, 2, sort);

		// when
		Page<Post> posts = postQueryDslRepository.searchPosts(searchCode, searchString, pageable);

		// then
		assertThat(posts).hasSize(2)
			.extracting("title", "content", "member")
			.containsExactly(tuple("제목24", "내용34", member2),
				tuple("제목23", "내용23", member2));
	}

	@DisplayName("글쓴이, 검색어, 페이징으로 게시글을 찾을 수 있다.")
	@Test
	void searchPostsName() {
		// given
		Member member1 = Member.create("aaa", "1234", "AAA");
		Member member2 = Member.create("bbb", "1234", "BBB");
		memberRepository.saveAll(List.of(member1, member2));

		Post post1 = Post.create("제목11", "내용11", member1);
		Post post2 = Post.create("제목12", "내용12", member1);
		Post post3 = Post.create("제목23", "내용23", member2);
		Post post4 = Post.create("제목24", "내용34", member2);
		postRepository.saveAll(List.of(post1, post2, post3, post4));

		SearchCode searchCode = SearchCode.NAME;
		String searchString = "B";
		Sort sort = Sort.by("createdDate").descending();
		Pageable pageable = PageRequest.of(0, 2, sort);

		// when
		Page<Post> posts = postQueryDslRepository.searchPosts(searchCode, searchString, pageable);

		// then
		assertThat(posts).hasSize(2)
			.extracting("title", "content", "member")
			.containsExactly(tuple("제목24", "내용34", member2),
				tuple("제목23", "내용23", member2));
	}

}