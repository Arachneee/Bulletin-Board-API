package bulletin.board.domain;

import static jakarta.persistence.FetchType.*;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.annotations.BatchSize;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;
import org.springframework.web.multipart.MultipartFile;

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
	@OneToMany(mappedBy = "post", cascade = CascadeType.ALL)
	private List<UploadFile> images = new ArrayList<>();

	@ManyToOne(fetch = LAZY)
	@JoinColumn(name = "member_id")
	private Member member;

	private Integer viewCount;

	@BatchSize(size = 10)
	@OneToMany(mappedBy = "post", cascade = CascadeType.ALL)
	private List<Comment> comments = new ArrayList<>();

	private boolean isDeleted = Boolean.FALSE;

	public static Post create(String title, String content, Member member) {
		Post post = new Post();

		post.setTitle(title);
		post.setContent(content);
		post.setMember(member);
		post.setViewCount(0);

		return post;
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

	public void view() {
		++viewCount;
	}

	public void update(String title, String content) {
		setTitle(title);
		setContent(content);
	}

	public boolean isWriter(Member member) {
		return this.member.getId().equals(member.getId());
	}

	public String getWriterName() {
		return member.getName();
	}
}
