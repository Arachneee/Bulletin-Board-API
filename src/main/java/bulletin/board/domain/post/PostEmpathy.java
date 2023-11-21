package bulletin.board.domain.post;

import bulletin.board.domain.BaseEntity;
import bulletin.board.domain.member.Member;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import static jakarta.persistence.FetchType.LAZY;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PostEmpathy extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "post_empathy_id")
    private Long id;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "post_id")
    private Post post;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    public static PostEmpathy create(Post post, Member member) {
        PostEmpathy postEmpathy = new PostEmpathy();
        postEmpathy.setMember(member);
        postEmpathy.setPost(post);

        return postEmpathy;
    }

    private void setPost(Post post) {
        this.post = post;
        post.addEmpathy(this);
    }

    private void setMember(Member member) {
        this.member = member;
    }

    public Long getMemberId() {
        return member.getId();
    }
}
