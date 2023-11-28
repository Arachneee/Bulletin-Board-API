package bulletin.board;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
public class TestDataInit {

//    private final InitService initService;

    @PostConstruct
    public void init() {
//        initService.dbInit1();
    }

    @Component
    @Transactional
    @RequiredArgsConstructor
    static class InitService {
//
//        private final MemberRepository memberRepository;
//        private final PostRepository postRepository;
//        private final CommentRepository commentRepository;
//        private final PasswordEncoder passwordEncoder;
//        public void dbInit1() {
//            List<Member> all = memberRepository.findAll();
//            all.forEach(member -> {
//                String password = member.getPassword();
//                String encoded = passwordEncoder.encode(password);
//                member.setPassword(encoded);
//            });
//
//        }
    }
}
