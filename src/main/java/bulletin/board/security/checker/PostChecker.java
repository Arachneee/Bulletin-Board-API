package bulletin.board.security.checker;

import bulletin.board.domain.member.Member;
import bulletin.board.domain.post.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PostChecker {

    private final PostRepository postRepository;

    public boolean isSelf(Long postId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Member member = (Member) authentication.getPrincipal();

        return postRepository.existsByIdAndMember(postId, member);
    }
}
