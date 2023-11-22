package bulletin.board.domain.member;


import bulletin.board.domain.BaseEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import java.util.Objects;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Where(clause = "is_deleted = false")
@SQLDelete(sql = "UPDATE member SET is_deleted = true where member_id = ?")
public class Member extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_id")
    private Long id;

    @Column(nullable = false, length = 30)
    private String loginId;

    @Column(nullable = false, length = 30)
    private String password;

    @Column(nullable = false, length = 30)
    private String name;

    private boolean isDeleted = Boolean.FALSE;


    public static Member create(final String loginId, final String password, final String name) {
        Member member = new Member();
        member.setLoginId(loginId);
        member.setPassword(password);
        member.setName(name);

        return member;
    }

    public static Member testCreate(String loginId, String password, String name, Long id) {
        Member member = new Member();
        member.setLoginId(loginId);
        member.setPassword(password);
        member.setName(name);
        member.setId(id);

        return member;
    }

    private void setId(Long id) {
        this.id = id;
    }

    private void setLoginId(String loginId) {
        this.loginId = loginId;
    }

    private void setPassword(String password) {
        this.password = password;
    }

    private void setName(String name) {
        this.name = name;
    }

    public void changeName(final String newName) {
        setName(newName);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Member member)) return false;
        return Objects.equals(getLoginId(), member.getLoginId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getLoginId());
    }
}
