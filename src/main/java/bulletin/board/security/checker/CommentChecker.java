package bulletin.board.security.checker;

import bulletin.board.domain.comment.repository.CommentRepository;
import bulletin.board.domain.member.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CommentChecker {

    private final CommentRepository commentRepository;

    public boolean isSelf(Long commentId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Member member = (Member) authentication.getPrincipal();

        return commentRepository.existsByIdAndMember(commentId, member);
    }
}
