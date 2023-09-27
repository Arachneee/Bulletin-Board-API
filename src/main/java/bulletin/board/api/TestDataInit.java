package bulletin.board.api;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import bulletin.board.core.domain.Comment;
import bulletin.board.core.domain.Member;
import bulletin.board.core.domain.Post;
import bulletin.board.core.repository.CommentRepository;
import bulletin.board.core.repository.MemberRepository;
import bulletin.board.core.repository.PostRepository;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class TestDataInit {

	private final MemberRepository memberRepository;
	private final PostRepository postRepository;
	private final CommentRepository commentRepository;

	@PostConstruct
	@Transactional
	public void init() {
		// Member member = memberRepository.findById(1L).get();
		// Post post = postRepository.findById(1L).get();
		//
		// for (int i = 1; i <= 100; i++) {
		// 	Comment comment = Comment.create("댓글test" + i, post, member);
		// 	commentRepository.save(comment);
		// }
	}
}
