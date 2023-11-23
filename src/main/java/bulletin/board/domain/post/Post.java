package bulletin.board.domain.post;

import bulletin.board.exceptions.constant.ErrorCode;
import bulletin.board.domain.BaseEntity;
import bulletin.board.domain.comment.Comment;
import bulletin.board.domain.member.Member;
import bulletin.board.exceptions.DuplicatedEmpathyException;
import bulletin.board.exceptions.SelfEmpathyException;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.BatchSize;
import org.hibernate.annotations.Formula;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import java.util.ArrayList;
import java.util.List;

import static jakarta.persistence.FetchType.LAZY;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Where(clause = "is_deleted = false")
@SQLDelete(sql = "UPDATE post SET is_deleted = true where post_id = ?")
public class Post extends BaseEntity {

	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "post_id")
	private Long id;

	@Column(nullable = false, length = 100)
	private String title;

	@Column(nullable = false, columnDefinition = "MEDIUMTEXT")
	private String content;

	@BatchSize(size = 10)
	@OneToMany(mappedBy = "post", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<UploadFile> images = new ArrayList<>();

	@ManyToOne(fetch = LAZY)
	@JoinColumn(name = "member_id")
	private Member member;

	private Integer viewCount;

	@BatchSize(size = 10)
	@OneToMany(mappedBy = "post", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<Comment> comments = new ArrayList<>();

	@OneToMany(mappedBy = "post", cascade = CascadeType.ALL)
	private List<PostEmpathy> postEmpathies = new ArrayList<>();

	@Formula("(select count(*) from post_empathy where post_empathy.post_id=post_id)")
	private int empathiesCount;

	private boolean isDeleted = Boolean.FALSE;

	public static Post create(final String title, final String content, final Member member) {
		Post post = new Post();

		post.setTitle(title);
		post.setContent(content);
		post.setMember(member);
		post.setViewCount(0);

		return post;
	}

	public void view() {
		++viewCount;
	}

	public void update(final String title, final String content) {
		setTitle(title);
		setContent(content);
	}

	public void addComment(final Comment comment) {
		comments.add(comment);
	}

	public void addImage(final UploadFile uploadFile) {
		images.add(uploadFile);
	}

	public void addEmpathy(final PostEmpathy postEmpathy) {
		validateCommentEmpathy(postEmpathy);
		postEmpathies.add(postEmpathy);
	}

	private void validateCommentEmpathy(final PostEmpathy postEmpathy) {
		validateWriter(postEmpathy);
		validateAlreadyEmpathized(postEmpathy);
	}

	public void validateWriter(final PostEmpathy postEmpathy) {
		if (postEmpathy.isWriter(member)) {
			throw new SelfEmpathyException(ErrorCode.SELF_EMPATHY);
		}
	}

	public void validateAlreadyEmpathized(final PostEmpathy postEmpathy) {
		if (isAlreadyEmpathized(postEmpathy)) {
			throw new DuplicatedEmpathyException(ErrorCode.DUPLICATED_EMPATHY);
		}
	}

	public boolean isAlreadyEmpathized(final PostEmpathy postEmpathy) {
		return postEmpathies.stream()
				.anyMatch(postEmpathy::equals);
	}

	public boolean isAlreadyEmpathized(final Member member) {
		return postEmpathies.stream()
				.anyMatch(empathy -> empathy.isWriter(member));
	}

	public boolean canEmpathy(final Member member) {
		return !(isAlreadyEmpathized(member) || isWriter(member));
	}

	public boolean isWriter(final Member member) {
		return this.member.equals(member);
	}

	private void setTitle(String title) {
		this.title = title;
	}

	private void setContent(String content) {
		this.content = content;
	}

	private void setMember(Member member) {
		this.member = member;
	}

	private void setViewCount(Integer viewCount) {
		this.viewCount = viewCount;
	}

	public String getWriterName() {
		return member.getName();
	}

	public Integer getEmpathyCount() {
		return postEmpathies.size();
	}

	public List<Long> getImageIds() {
		return images.stream()
				.map(UploadFile::getId)
				.toList();
	}
}
