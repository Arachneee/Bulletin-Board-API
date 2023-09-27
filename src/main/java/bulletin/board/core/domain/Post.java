package bulletin.board.core.domain;

import static jakarta.persistence.FetchType.*;

import java.util.ArrayList;
import java.util.List;

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
import lombok.Getter;

@Entity
@Getter
public class Post extends BaseEntity {

	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "post_id")
	private Long id;

	@Column(nullable = false, length = 100)
	private String title;

	@Lob
	@Column(nullable = false)
	private String content;

	@ManyToOne(fetch = LAZY)
	@JoinColumn(name = "member_id")
	private Member member;

	private Integer viewCount;

	@OneToMany(mappedBy = "post", cascade = CascadeType.ALL)
	private List<Comment> comments = new ArrayList<>();

	protected Post() {
	}
	public static Post create(String title, String content, Member member) {
		Post post = new Post();

		post.setTitle(title);
		post.setContent(content);
		post.setMember(member);
		post.setViewCount(0);

		return post;
	}

	public static Post createRowMap(String title, String content, Member member, Integer viewCount) {
		Post post = new Post();

		post.setTitle(title);
		post.setContent(content);
		post.setMember(member);
		post.setViewCount(viewCount);

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
		this.title = title;
		this.content = content;
	}
}
