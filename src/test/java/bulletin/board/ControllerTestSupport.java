package bulletin.board;

import bulletin.board.api.controller.comment.CommentController;
import bulletin.board.api.controller.comment.CommentEmpathyController;
import bulletin.board.api.controller.member.MemberController;
import bulletin.board.api.controller.post.PostController;
import bulletin.board.api.controller.post.PostEmpathyController;
import bulletin.board.api.service.comment.CommentEmpathyService;
import bulletin.board.api.service.comment.CommentService;
import bulletin.board.api.service.member.MemberService;
import bulletin.board.api.service.post.PostEmpathyService;
import bulletin.board.api.service.post.PostService;
import bulletin.board.api.service.post.UploadFileService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(controllers = {
        MemberController.class,
        PostController.class,
        CommentController.class,
        PostEmpathyController.class,
        CommentEmpathyController.class
})
public abstract class ControllerTestSupport {
    @Autowired
    protected MockMvc mockMvc;

    @Autowired
    protected ObjectMapper objectMapper;

    @MockBean
    protected PostService postService;

    @MockBean
    protected PostEmpathyService postEmpathyService;

    @MockBean
    protected MemberService memberService;

    @MockBean
    protected CommentService commentService;

    @MockBean
    protected CommentEmpathyService commentEmpathyService;

    @MockBean
    protected UploadFileService uploadFileService;
}
