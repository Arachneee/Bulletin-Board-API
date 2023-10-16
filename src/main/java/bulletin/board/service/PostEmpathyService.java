package bulletin.board.service;

import bulletin.board.constant.ErrorCode;
import bulletin.board.domain.Member;
import bulletin.board.domain.Post;
import bulletin.board.domain.PostEmpathy;
import bulletin.board.exceptions.EntityNotFoundException;
import bulletin.board.repository.PostEmpathyRepository;
import bulletin.board.repository.PostRepository;
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
