package bulletin.board.security.checker;

import bulletin.board.domain.member.Member;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
public class MemberChecker {

    public boolean isSelf(Long memberId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Member principal = (Member) authentication.getPrincipal();

        return principal.getId().equals(memberId);
    }
}
