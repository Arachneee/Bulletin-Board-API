package bulletin.board.auth;

import bulletin.board.domain.comment.repository.CommentRepository;
import bulletin.board.domain.post.repository.PostRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.servlet.HandlerInterceptor;

// Spring Security 사용하기 전 버전
@Slf4j
@RequiredArgsConstructor
public class AuthorityInterceptor implements HandlerInterceptor {

    private final PostRepository postRepository;
    private final CommentRepository commentRepository;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
//        String requestURI = request.getRequestURI();
//        String method = request.getMethod();
//        HttpSession session = request.getSession(false);
//        log.info("AuthorityInterceptor 실행 requestURI={}, session.isNull = {}", requestURI, session == null);
//
//        // 로그인 여부 확인
//        if (session == null || session.getAttribute(SessionConst.LOGIN_MEMBER) == null) {
//            request.getRequestDispatcher("/error/login").forward(request, response);
//            return false;
//        }
//
//        if (method.equals("GET") || method.equals("POST")) {
//            return true;
//        }
//
//        // 로그인 사용자 (수정, 삭제) 권한 확인
//        Member loginMember = (Member)session.getAttribute(SessionConst.LOGIN_MEMBER);
//        if (isNotAuthorityMember(requestURI, loginMember)) {
//            request.getRequestDispatcher("/error/authority").forward(request, response);
//            log.info("권한 필요");
//            return false;
//        }

        return true;
    }

//    private boolean isNotAuthorityMember(String requestURI, Member loginMember) {
//        String[] urlSplit = requestURI.split("/");
//
//
//        // url : /members/{memberId} method : PATH, DELETE
//        if (requestURI.matches("^/members/[0-9]+$")) {
//            Long memberId = Long.valueOf(urlSplit[2]);
//            return !loginMember.getId().equals(memberId);
//        }
//
//        // url : /posts/{postId} method : PATH, DELETE
//        if (requestURI.matches("^/posts/[0-9]+$")) {
//            Long postId = Long.valueOf(urlSplit[2]);
//
//            return !postRepository.existsByIdAndMember(postId, loginMember);
//        }
//
//        // url : /posts/{postId}/comments/{commentId} method : PATH, DELETE
//        if (requestURI.matches("^/posts/[0-9]+/comments/[0-9]+$")) {
//            Long commentsId = Long.valueOf(urlSplit[4]);
//
//            return !commentRepository.existsByIdAndMember(commentsId, loginMember);
//        }
//
//        return false;
//    }

}
