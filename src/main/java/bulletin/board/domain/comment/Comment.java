package bulletin.board.domain.comment;

import bulletin.board.domain.BaseEntity;
import bulletin.board.domain.member.Member;
import bulletin.board.domain.post.Post;
import bulletin.board.exceptions.DuplicatedEmpathyException;
import bulletin.board.exceptions.SelfEmpathyException;
import bulletin.board.exceptions.constant.ErrorCode;
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
@SQLDelete(sql = "UPDATE comment SET is_deleted = true where comment_id = ?")
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

	@OneToMany(mappedBy = "comment", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<CommentEmpathy> commentEmpathies = new ArrayList<>();

	@ManyToOne(fetch = LAZY)
	@JoinColumn(name = "parent_comment_id")
	private Comment parentComment;

	@BatchSize(size = 10)
	@OneToMany(mappedBy = "parentComment", cascade = CascadeType.ALL)
	private List<Comment> replies = new ArrayList<>();

	@Formula("(select count(*) from comment_empathy where comment_empathy.comment_id=comment_id)")
	private int empathiesCount;

	private boolean isDeleted = Boolean.FALSE;


	public static Comment create(final String content, final Post post, final Member member) {
		Comment comment = new Comment();
		comment.setContent(content);
		comment.setPost(post);
		comment.setMember(member);

		return comment;
	}

	public static Comment createReply(final String content, final Comment parentComment, final Member member) {
		Comment comment = new Comment();
		comment.setParentComment(parentComment);
		comment.setContent(content);
		comment.setPost(parentComment.getPost());
		comment.setMember(member);

		return comment;
	}

	public void update(final String content) {
		setContent(content);
	}

	private void addReply(final Comment comment) {
		replies.add(comment);
	}

	public void addEmpathy(final CommentEmpathy commentEmpathy) {
		validateCommentEmpathy(commentEmpathy);
		commentEmpathies.add(commentEmpathy);
	}

	private void validateCommentEmpathy(final CommentEmpathy commentEmpathy) {
		validateWriter(commentEmpathy);
		validateAlreadyEmpathized(commentEmpathy);
	}

	public void validateWriter(final CommentEmpathy commentEmpathy) {
		if (commentEmpathy.isWriter(member)) {
			throw new SelfEmpathyException(ErrorCode.SELF_EMPATHY);
		}
	}

	public void validateAlreadyEmpathized(final CommentEmpathy commentEmpathy) {
		if (isAlreadyEmpathized(commentEmpathy)) {
			throw new DuplicatedEmpathyException(ErrorCode.DUPLICATED_EMPATHY);
		}
	}

	public boolean isAlreadyEmpathized(final CommentEmpathy commentEmpathy) {
		return commentEmpathies.stream()
			.anyMatch(commentEmpathy::equals);
	}

	public boolean isAlreadyEmpathized(final Member member) {
		return commentEmpathies.stream()
				.anyMatch(commentEmpathy -> commentEmpathy.isWriter(member));
	}

	public boolean isWriter(Member member) {
		return this.member.equals(member);
	}

	public boolean canEmpathy(Member member) {
		return !(isAlreadyEmpathized(member) || isWriter(member));
	}

	public String getWriterName() {
		return member.getName();
	}

	public Comment getRootComment() {
		return parentComment == null ? this : getRootComment();
	}

	public Integer getEmpathyCount() {
		return commentEmpathies.size();
	}

	private void setContent(String content) {
		this.content = content;
	}

	private void setPost(final Post post) {
		this.post = post;
		post.addComment(this);
	}

	private void setMember(Member member) {
		this.member = member;
	}

	private void setParentComment(final Comment parentComment) {
		this.parentComment = parentComment;
		parentComment.addReply(this);
	}
}
