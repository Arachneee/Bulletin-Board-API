package bulletin.board.config;

import bulletin.board.api.service.login.LoginMemberArgumentResolver;
import bulletin.board.domain.comment.repository.CommentRepository;
import bulletin.board.domain.post.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

@Configuration
@RequiredArgsConstructor
public class WebConfig implements WebMvcConfigurer {

    private final PostRepository postRepository;
    private final CommentRepository commentRepository;

    private static final long MAX_AGE_SECS = 3600;

    @Override
    public void addInterceptors(final InterceptorRegistry registry) {
//        registry.addInterceptor(new AuthorityInterceptor(postRepository, commentRepository))
//                .addPathPatterns("/**")
//                .excludePathPatterns("/", "/members", "/login", "/posts", "/css/**", "/*.ico", "/error/**", "/vue/**");
    }

    @Override
    public void addArgumentResolvers(final List<HandlerMethodArgumentResolver> resolvers) {
        resolvers.add(new LoginMemberArgumentResolver());
    }

    @Override
    public void addCorsMappings(final CorsRegistry registry) {
        registry.addMapping("/**")
            .allowedOrigins("*")
            .allowedMethods("GET", "POST", "PUT", "PATCH", "DELETE")
            .maxAge(MAX_AGE_SECS);
    }
}
