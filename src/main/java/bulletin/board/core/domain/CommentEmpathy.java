package bulletin.board.core.domain;

import static jakarta.persistence.FetchType.*;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Getter;

@Entity
@Getter
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

    protected CommentEmpathy() {
    }

    public static CommentEmpathy create(Comment comment, Member member) {
        CommentEmpathy commentEmpathy = new CommentEmpathy();
        commentEmpathy.setComment(comment);
        commentEmpathy.setMember(member);

        return commentEmpathy;
    }


    private void setComment(Comment comment) {
        this.comment = comment;
        comment.addEmpathy(this);
    }

    private void setMember(Member member) {
        this.member = member;
    }

    public Long getMemberId() {
        return member.getId();
    }
}
