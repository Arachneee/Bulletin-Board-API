package bulletin.board.api.service.post;

import bulletin.board.IntegrationTestSupport;
import bulletin.board.domain.member.Member;
import bulletin.board.domain.member.MemberRepository;
import bulletin.board.domain.post.Post;
import bulletin.board.domain.post.repository.PostEmpathyRepository;
import bulletin.board.domain.post.repository.PostRepository;
import bulletin.board.exceptions.DuplicatedEmpathyException;
import bulletin.board.exceptions.SelfEmpathyException;
import bulletin.board.exceptions.constant.ErrorCode;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.assertj.core.api.Assertions.*;

@Transactional
class PostEmpathyServiceTest extends IntegrationTestSupport {

    @Autowired
    private PostEmpathyService postEmpathyService;

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private PostEmpathyRepository postEmpathyRepository;

    Member member1;
    Member member2;
    Post savedPost;
    @BeforeEach
    void setUp() {
        member1 = Member.create("aaa", "1234", "AAA");
        member2 = Member.create("bbb", "1234", "BBB");
        memberRepository.saveAll(List.of(member1, member2));

        Post post = Post.create("제목1", "내용1", member1);
        savedPost = postRepository.save(post);
    }

    @DisplayName("게시글에 공감할 수 있다.")
    @Test
    void create() {
    	// given
        Long postId = savedPost.getId();

    	// when
        postEmpathyService.create(member2, postId);

    	// then
        assertThat(savedPost.getPostEmpathies())
                .extracting("post", "member")
                .containsExactly(tuple(savedPost, member2));
    }

    @DisplayName("자신의 게시글에는 공감할 수 없다.")
    @Test
    void createSelf() {
        // given
        Long postId = savedPost.getId();

        // when // then
        assertThatThrownBy(() -> postEmpathyService.create(member1, postId))
                .isInstanceOf(SelfEmpathyException.class)
                .hasMessage(ErrorCode.SELF_EMPATHY.getMessage());
    }

    @DisplayName("중복으로 공감할 수 없다.")
    @Test
    void createDuplicated() {
        // given
        Long postId = savedPost.getId();

        // when
        postEmpathyService.create(member2, postId);

        // then
        assertThatThrownBy(() -> postEmpathyService.create(member2, postId))
                .isInstanceOf(DuplicatedEmpathyException.class)
                .hasMessage(ErrorCode.DUPLICATED_EMPATHY.getMessage());
    }
}