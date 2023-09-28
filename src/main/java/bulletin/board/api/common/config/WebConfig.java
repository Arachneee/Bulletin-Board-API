package bulletin.board.api.common.config;

import java.util.List;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import bulletin.board.api.login.argumentresolver.LoginMemberArgumentResolver;
import bulletin.board.api.login.interceptor.AuthorityInterceptor;
import bulletin.board.core.repository.CommentRepository;
import bulletin.board.core.repository.PostRepository;
import lombok.RequiredArgsConstructor;

@Configuration
@RequiredArgsConstructor
public class WebConfig implements WebMvcConfigurer {

    private final PostRepository postRepository;
    private final CommentRepository commentRepository;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new AuthorityInterceptor(postRepository, commentRepository))
                .addPathPatterns("/**")
                .excludePathPatterns("/", "/members", "/login", "/css/**", "/*.ico", "/error/**");

    }

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
        resolvers.add(new LoginMemberArgumentResolver());
    }
}
