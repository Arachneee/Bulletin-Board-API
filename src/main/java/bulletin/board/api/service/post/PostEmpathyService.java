package bulletin.board.api.service.post;

import bulletin.board.exceptions.constant.ErrorCode;
import bulletin.board.domain.member.Member;
import bulletin.board.domain.post.Post;
import bulletin.board.domain.post.PostEmpathy;
import bulletin.board.exceptions.EntityNotFoundException;
import bulletin.board.domain.post.repository.PostEmpathyRepository;
import bulletin.board.domain.post.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class PostEmpathyService {

    private final PostEmpathyRepository postEmpathyRepository;
    private final PostRepository postRepository;

    @Transactional
    public void createPostEmpathy(Member member, Long postId) {
        Post findPost = postRepository.findWithEmpathyById(postId)
                .orElseThrow(() -> new EntityNotFoundException(ErrorCode.POST_NOT_FOUND));

        PostEmpathy postEmpathy = PostEmpathy.create(findPost, member);
        postEmpathyRepository.save(postEmpathy);
    }

    @Transactional
    public void deletePostEmpathy(Member member, Long postId) {
        PostEmpathy postEmpathy = postEmpathyRepository.findByMemberAndPostId(member, postId)
                .orElseThrow(() -> new EntityNotFoundException(ErrorCode.EMPATHY_NOT_FOUND));

        postEmpathyRepository.delete(postEmpathy);
    }
}
