package bulletin.board;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import bulletin.board.domain.comment.Comment;
import bulletin.board.domain.member.Member;
import bulletin.board.domain.post.Post;
import bulletin.board.domain.comment.repository.CommentRepository;
import bulletin.board.domain.member.MemberRepository;
import bulletin.board.domain.post.repository.PostRepository;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class TestDataInit {

	private final InitService initService;

	@PostConstruct
	public void init() {
		// initService.dbInit1();
	}

	@Component
	@Transactional
	@RequiredArgsConstructor
	static class InitService {

		private final MemberRepository memberRepository;
		private final PostRepository postRepository;
		private final CommentRepository commentRepository;
		public void dbInit1() {
			Member member = memberRepository.findById(2L).get();
			Post post = postRepository.findById(1L).get();

			for (int i = 10; i <= 39; i++) {
				Comment comment = Comment.create("댓글test" + i, post, member);
				commentRepository.save(comment);
			}
		}
	}
}
