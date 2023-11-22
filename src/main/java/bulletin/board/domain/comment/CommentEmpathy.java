package bulletin.board.domain.comment;

import bulletin.board.domain.BaseEntity;
import bulletin.board.domain.member.Member;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Objects;

import static jakarta.persistence.FetchType.LAZY;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CommentEmpathy extends BaseEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "comment_empathy_id")
    private Long id;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "comment_id")
    private Comment comment;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "member_id")
    private Member member;


    public static CommentEmpathy create(Comment comment, Member member) {
        CommentEmpathy commentEmpathy = new CommentEmpathy();
        commentEmpathy.setMember(member);
        commentEmpathy.setComment(comment);

        return commentEmpathy;
    }

    public boolean isWriter(final Member member) {
        return this.member.equals(member);
    }

    private void setComment(final Comment comment) {
        this.comment = comment;
        comment.addEmpathy(this);
    }

    private void setMember(Member member) {
        this.member = member;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof CommentEmpathy that)) return false;
        return Objects.equals(getComment(), that.getComment()) && Objects.equals(getMember(), that.getMember());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getComment(), getMember());
    }
}
