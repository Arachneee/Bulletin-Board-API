package bulletin.board.api.service.post;

import bulletin.board.IntegrationTestSupport;
import bulletin.board.domain.member.Member;
import bulletin.board.domain.member.MemberRepository;
import bulletin.board.domain.post.Post;
import bulletin.board.domain.post.repository.PostRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static org.assertj.core.api.Assertions.assertThat;

@Transactional
class PostServiceTest extends IntegrationTestSupport {

	private static final int COUNT = 100;
	private static final ExecutorService service = Executors.newFixedThreadPool(COUNT);

	@Autowired
	PostRepository postRepository;

	@Autowired
	MemberRepository memberRepository;

	@Autowired
	PostService postService;

	@DisplayName("게시글 조회수 동시성 문제가 없다.")
	@Test
	void findPost() throws InterruptedException {
		// given
		Member member = Member.create("abc", "1234", "ABC");
		memberRepository.save(member);

		Post post = Post.create("제목1", "내용1", member);
		Post savePost = postRepository.save(post);
		Long id = savePost.getId();

		CountDownLatch latch = new CountDownLatch(COUNT);
		Integer before = savePost.getViewCount();

		// when
		for (int i = 0; i < COUNT; i++) {
			service.execute(() -> {
				postService.findPost(member, id);
				latch.countDown();
			});
		}

		// then
		latch.await();
		assertThat(postRepository.findById(id).get().getViewCount()).isEqualTo(before + COUNT);
	}

}