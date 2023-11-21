package bulletin.board.api.service.login;

import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import bulletin.board.domain.member.Member;

public class LoginMemberArgumentResolver implements HandlerMethodArgumentResolver {
    @Override
    public boolean supportsParameter(MethodParameter parameter) {

        boolean hasLoginAnnotation = parameter.hasParameterAnnotation(Login.class);
        boolean hasMemberType = Member.class.isAssignableFrom(parameter.getParameterType());

        return hasLoginAnnotation && hasMemberType;
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {


        return Member.testCreate("aaa", "1234", "nameA", 1L);
//        HttpServletRequest request = (HttpServletRequest) webRequest.getNativeRequest();
//        HttpSession session = request.getSession(false);
//        if (session == null) {
//            return null;
//        }
//
//        return session.getAttribute(SessionConst.LOGIN_MEMBER);
    }
}
