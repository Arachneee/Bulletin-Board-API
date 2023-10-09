package bulletin.board.domain;

import static jakarta.persistence.FetchType.*;

import java.util.ArrayList;
import java.util.List;

import bulletin.board.constant.ErrorCode;
import bulletin.board.exceptions.DuplicatedCommentEmpathyException;
import bulletin.board.exceptions.SelfEmpathyException;
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
import org.hibernate.annotations.BatchSize;

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

	@ManyToOne(fetch = LAZY)
	@JoinColumn(name = "parent_comment_id")
	private Comment parentComment;

	@BatchSize(size = 10)
	@OneToMany(mappedBy = "parentComment", cascade = CascadeType.ALL)
	private List<Comment> replies = new ArrayList<>();


	public static Comment create(String content, Post post, Member member) {
		Comment comment = new Comment();
		comment.setContent(content);
		comment.setPost(post);
		comment.setMember(member);

		return comment;
	}

	public static Comment createReply(String content, Comment parentComment, Member member) {
		Comment comment = new Comment();
		comment.setParentComment(parentComment);
		comment.setContent(content);
		comment.setPost(parentComment.getPost());
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

	private void setParentComment(Comment parentComment) {
		this.parentComment = parentComment;
		parentComment.getReplies().add(this);
	}

	public void update(String content) {
		setContent(content);
	}

	public Integer getEmpathyCount() {
		return commentEmpathies.size();
	}

	public void addEmpathy(CommentEmpathy commentEmpathy) {
		validateCommentEmpathy(commentEmpathy.getMember());
		commentEmpathies.add(commentEmpathy);
	}

	private void validateCommentEmpathy(Member empathyMember) {
		validateWriter(empathyMember);
		validateAlreadyEmpathized(empathyMember);
	}

	public void validateWriter(Member member) {
		if (isWriter(member)) {
			throw new SelfEmpathyException(ErrorCode.SELF_EMPATHY);
		}
	}

	public void validateAlreadyEmpathized(Member member) {
		if (isAlreadyEmpathized(member)) {
			throw new DuplicatedCommentEmpathyException(ErrorCode.DUPLICATED_EMPATHY);
		}
	}

	public boolean isAlreadyEmpathized(Member member) {
		return commentEmpathies.stream()
			.map(CommentEmpathy::getMemberId)
			.toList()
			.contains(member.getId());
	}

	public boolean isWriter(Member member) {
		return this.member.getId().equals(member.getId());
	}

	public boolean canEmpathy(Member member) {
		return !(isAlreadyEmpathized(member) || isWriter(member));
	}

	public String getWriterName() {
		return member.getName();
	}
}
