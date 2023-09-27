package bulletin.board.core.domain;

import static jakarta.persistence.FetchType.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

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

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Comment extends BaseEntity {

	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "comment_id")
	private Long id;

	@Lob
	@Column(nullable = false)
	private String content;

	@ManyToOne(fetch = LAZY)
	@JoinColumn(name = "post_id")
	private Post post;

	@ManyToOne(fetch = LAZY)
	@JoinColumn(name = "member_id")
	private Member member;

	@OneToMany(mappedBy = "comment", cascade = CascadeType.ALL)
	private List<CommentEmpathy> commentEmpathies = new ArrayList<>();


	public static Comment create(String content, Post post, Member member) {
		Comment comment = new Comment();
		comment.setContent(content);
		comment.setPost(post);
		comment.setMember(member);

		return comment;
	}

	private void setContent(String content) {
		this.content = content;
	}

	private void setPost(Post post) {
		this.post = post;
		post.getComments().add(this);
	}

	private void setMember(Member member) {
		this.member = member;
	}

	public void update(String content) {
		setContent(content);
	}

	public Integer getEmpathyCount() {
		return commentEmpathies.size();
	}

	public void addEmpathy(CommentEmpathy commentEmpathy) {
		commentEmpathies.add(commentEmpathy);
	}


	public boolean isWriter(Member member) {
		return this.member.getId().equals(member.getId());
	}

	public boolean isAlreadyEmpathized(Member member) {
		return commentEmpathies.stream()
			.map(CommentEmpathy::getMemberId)
			.collect(Collectors.toList())
			.contains(member.getId());
	}

	public int empathyCountDiff(Comment other) {
		return this.getEmpathyCount() - other.getEmpathyCount();
	}

}
