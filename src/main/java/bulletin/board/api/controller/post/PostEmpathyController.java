package bulletin.board.api.controller.post;

import bulletin.board.api.service.login.Login;
import bulletin.board.domain.member.Member;
import bulletin.board.api.service.post.PostEmpathyService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/posts/{postId}/empathy")
public class PostEmpathyController {

    private final PostEmpathyService postEmpathyService;

    @PostMapping("")
    public ResponseEntity<Void> createPostEmpathy(@Login Member member,
                                                     @PathVariable Long postId) {
        postEmpathyService.createPostEmpathy(member, postId);

        return ResponseEntity.accepted().build();
    }

    @DeleteMapping("")
    public ResponseEntity<Void> deletePostEmpathy(@Login Member member, @PathVariable Long postId) {
        postEmpathyService.deletePostEmpathy(member, postId);

        return ResponseEntity.noContent().build();
    }
}
