package bulletin.board.api.service.post;

import bulletin.board.domain.member.Member;
import bulletin.board.domain.post.Post;
import bulletin.board.domain.post.PostEmpathy;
import bulletin.board.domain.post.repository.PostEmpathyRepository;
import bulletin.board.domain.post.repository.PostRepository;
import bulletin.board.exceptions.EntityNotFoundException;
import bulletin.board.exceptions.constant.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class PostEmpathyService {

    private final PostEmpathyRepository postEmpathyRepository;
    private final PostRepository postRepository;

    @Transactional
    public void create(final Member member, final Long postId) {
        final Post findPost = postRepository.findWithEmpathyById(postId)
                .orElseThrow(() -> new EntityNotFoundException(ErrorCode.POST_NOT_FOUND));

        final PostEmpathy postEmpathy = PostEmpathy.create(findPost, member);
        postEmpathyRepository.save(postEmpathy);
    }

    @Transactional
    public void delete(final Member member, final Long postId) {
        final PostEmpathy postEmpathy = postEmpathyRepository.findByMemberAndPostId(member, postId)
                .orElseThrow(() -> new EntityNotFoundException(ErrorCode.EMPATHY_NOT_FOUND));

        postEmpathyRepository.delete(postEmpathy);
    }
}
