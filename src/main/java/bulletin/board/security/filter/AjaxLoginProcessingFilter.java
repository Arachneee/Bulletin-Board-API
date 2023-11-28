package bulletin.board.security.filter;

import bulletin.board.security.token.AjaxAuthenticationToken;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.context.DelegatingSecurityContextRepository;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.security.web.context.RequestAttributeSecurityContextRepository;
import org.springframework.security.web.context.SecurityContextRepository;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.util.StringUtils;

import java.io.IOException;

public class AjaxLoginProcessingFilter extends AbstractAuthenticationProcessingFilter {

    private ObjectMapper objectMapper = new ObjectMapper();

    public AjaxLoginProcessingFilter(HttpSecurity http, AuthenticationManager authenticationManager) {
        super(new AntPathRequestMatcher("/api/login", "POST"), authenticationManager);
        setSecurityContextRepository(getSecurityContextRepository(http));
    }

    SecurityContextRepository getSecurityContextRepository(HttpSecurity http) {
        SecurityContextRepository securityContextRepository = http.getSharedObject(SecurityContextRepository.class);
        if (securityContextRepository == null) {
            securityContextRepository = new DelegatingSecurityContextRepository(
                    new RequestAttributeSecurityContextRepository(), new HttpSessionSecurityContextRepository());
        }
        return securityContextRepository;
    }
    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException, IOException, ServletException {

        if (!isAjax(request)) {
            throw new IllegalStateException("Authentication is not supported");
        }

        MemberDto memberDto = objectMapper.readValue(request.getReader(), MemberDto.class);
        if (!StringUtils.hasText(memberDto.getUsername()) || !StringUtils.hasText(memberDto.getPassword())) {
            throw new IllegalArgumentException("Username or Password is empty");
        }

        AjaxAuthenticationToken authenticationToken = new AjaxAuthenticationToken(memberDto.getUsername(), memberDto.getPassword());

        return getAuthenticationManager().authenticate(authenticationToken);
    }

    private boolean isAjax(HttpServletRequest request) {

        if ("XMLHttpRequest".equals(request.getHeader("X-Requested-With"))) {
            return true;
        }

        return false;
    }
}
