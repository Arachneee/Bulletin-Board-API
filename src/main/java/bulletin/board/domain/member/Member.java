package bulletin.board.domain.member;


import bulletin.board.domain.BaseEntity;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import java.io.Serializable;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EqualsAndHashCode(of = "id", callSuper = false)
@Where(clause = "is_deleted = false")
@SQLDelete(sql = "UPDATE member SET is_deleted = true where member_id = ?")
public class Member extends BaseEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_id")
    private Long id;

    @Column(nullable = false, length = 30)
    private String loginId;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false, length = 30)
    private String name;

    @Enumerated(value = EnumType.STRING)
    private Role role = Role.USER;

//    @ManyToMany(fetch = FetchType.LAZY, cascade={CascadeType.ALL})
//    @JoinTable(name = "member_roles", joinColumns = { @JoinColumn(name = "member_id") }, inverseJoinColumns = {
//            @JoinColumn(name = "role_id") })
//    private Set<Role> roles = new HashSet<>();

    private boolean isDeleted = Boolean.FALSE;


    public static Member create(final String loginId, final String password, final String name) {
        Member member = new Member();
        member.setLoginId(loginId);
        member.setPassword(password);
        member.setName(name);

        return member;
    }

    private void setLoginId(String loginId) {
        this.loginId = loginId;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    private void setName(String name) {
        this.name = name;
    }

    public void changeName(final String newName) {
        setName(newName);
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public boolean isAdmin() {
        return role.isAdmin();
    }
}
