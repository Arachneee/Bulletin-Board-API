package bulletin.board.security.service;

import bulletin.board.domain.member.Member;
import bulletin.board.domain.member.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

// 데이터베이스에서 정보를 얻어 인증
// 5 -> 6 으로 바뀌면서 Config에서 주입하지 않아도 됨
@RequiredArgsConstructor
@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final MemberRepository memberRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        System.out.println(username);
        Optional<Member> member = memberRepository.findByLoginId(username);

        if (member.isEmpty()) {
            throw new UsernameNotFoundException("UsernameNotFoundException");
        }

        List<GrantedAuthority> roles = List.of(new SimpleGrantedAuthority(member.get().getRole().name()));

        AccountContext accountContext = new AccountContext(member.get(), roles);

        return accountContext;
    }
}
