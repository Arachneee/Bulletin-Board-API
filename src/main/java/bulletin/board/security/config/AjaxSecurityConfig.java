package bulletin.board.security.config;

import bulletin.board.security.common.AjaxLoginAuthenticationEntryPoint;
import bulletin.board.security.filter.AjaxLoginProcessingFilter;
import bulletin.board.security.handler.AjaxAccessDeniedHandler;
import bulletin.board.security.handler.AjaxAuthenticationFailureHandler;
import bulletin.board.security.handler.AjaxAuthenticationSuccessHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Order(0)
@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class AjaxSecurityConfig {

    private final AuthenticationConfiguration authenticationConfiguration;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

    @Bean
    @Order(1)
    public SecurityFilterChain apiFilterChain(HttpSecurity http) throws Exception {
        http
                .securityMatcher("/api/**")
                .csrf(AbstractHttpConfigurer::disable)
                .addFilterBefore(ajaxLoginProcessingFilter(http), UsernamePasswordAuthenticationFilter.class)
                .authorizeHttpRequests(request -> request
                        .requestMatchers("/api/posts").hasAuthority("MANAGER")
                        .anyRequest().authenticated()
                );

        http
                .exceptionHandling(exception -> exception
                        .authenticationEntryPoint(new AjaxLoginAuthenticationEntryPoint())
                        .accessDeniedHandler(ajaxAccessDeniedHandler())
                );


        return http.build();
    }

    @Bean
    @Order(2)
    public SecurityFilterChain h2ConsoleSecurityFilterChain(HttpSecurity http) throws Exception {
        return http
                .securityMatcher(AntPathRequestMatcher.antMatcher("/h2-console/**"))
                .authorizeHttpRequests(auth -> {
                    auth.requestMatchers(AntPathRequestMatcher.antMatcher("/h2-console/**")).permitAll();
                })
                .csrf(csrf -> csrf.ignoringRequestMatchers(AntPathRequestMatcher.antMatcher("/h2-console/**")))
                .headers(AbstractHttpConfigurer::disable)
                .build();

    }

    @Bean
    public AjaxLoginProcessingFilter ajaxLoginProcessingFilter(HttpSecurity http) throws Exception {
        AjaxLoginProcessingFilter filter = new AjaxLoginProcessingFilter(http, authenticationConfiguration.getAuthenticationManager());
        filter.setAuthenticationManager(authenticationManager(authenticationConfiguration));
        filter.setAuthenticationSuccessHandler(ajaxAuthenticationSuccessHandler());
        filter.setAuthenticationFailureHandler(ajaxAuthenticationFailureHandler());
        return filter;
    }

    @Bean
    public AccessDeniedHandler ajaxAccessDeniedHandler() {
        return new AjaxAccessDeniedHandler();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public AuthenticationSuccessHandler ajaxAuthenticationSuccessHandler() {
        return new AjaxAuthenticationSuccessHandler();
    }

    @Bean
    public AuthenticationFailureHandler ajaxAuthenticationFailureHandler() {
        return new AjaxAuthenticationFailureHandler();
    }
}
