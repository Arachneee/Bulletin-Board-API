package bulletin.board.interceptor;

import org.springframework.web.servlet.HandlerInterceptor;

import bulletin.board.session.SessionConst;
import bulletin.board.domain.Comment;
import bulletin.board.domain.Member;
import bulletin.board.domain.Post;
import bulletin.board.repository.CommentRepository;
import bulletin.board.repository.PostRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
public class AuthorityInterceptor implements HandlerInterceptor {

    private final PostRepository postRepository;
    private final CommentRepository commentRepository;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String requestURI = request.getRequestURI();
        String method = request.getMethod();
        HttpSession session = request.getSession(false);
        log.info("AuthorityInterceptor 실행 requestURI={}, session.isNull = {}", requestURI, session == null);

        // 로그인 여부 확인
        if (session == null || session.getAttribute(SessionConst.LOGIN_MEMBER) == null) {
            request.getRequestDispatcher("/error/login").forward(request, response);
            return false;
        }

        if (method.equals("GET") || method.equals("POST")) {
            return true;
        }

        // 로그인 사용자 (수정, 삭제) 권한 확인
        Member loginMember = (Member)session.getAttribute(SessionConst.LOGIN_MEMBER);
        if (isNotAuthorityMember(requestURI, loginMember)) {
            request.getRequestDispatcher("/error/authority").forward(request, response);
            log.info("권한 필요");
            return false;
        }

        return true;
    }

    private boolean isNotAuthorityMember(String requestURI, Member loginMember) {
        String[] urlSplit = requestURI.split("/");
        Long loginMemberId = loginMember.getId();

        // 회원 Update, Delete
        if (requestURI.matches("^/members/[0-9]+$")) {
            Long memberId = Long.valueOf(urlSplit[2]);
            return !loginMemberId.equals(memberId);
        }

        // 게시글 Update, Delete
        if (requestURI.matches("^/posts/[0-9]+$")) {
            Long postId = Long.valueOf(urlSplit[2]);
            Post post = postRepository.findById(postId).orElse(null);
            return post == null || !post.getMember().getId().equals(loginMemberId);
        }

        // 댓글 Update, Delete
        if (requestURI.matches("^/posts/[0-9]+/comments/[0-9]+$")) {
            Long commentsId = Long.valueOf(urlSplit[4]);
            Comment comment = commentRepository.findById(commentsId).orElse(null);
            return comment == null || !comment.getMember().getId().equals(loginMemberId);
        }

        return false;
    }

}
