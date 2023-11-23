package bulletin.board.api.controller.post;

import bulletin.board.api.controller.post.request.PostSearchRequest;
import bulletin.board.api.service.post.PostService;
import bulletin.board.api.service.post.UploadFileService;
import bulletin.board.api.service.post.response.PostResponse;
import bulletin.board.domain.comment.repository.CommentRepository;
import bulletin.board.domain.member.Member;
import bulletin.board.domain.post.Post;
import bulletin.board.domain.post.repository.PostRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ActiveProfiles("test")
@WebMvcTest(PostController.class)
class PostControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    PostService postService;

    @MockBean
    PostRepository postRepository;

    @MockBean
    CommentRepository commentRepository;

    @MockBean
    UploadFileService uploadFileService;

    @DisplayName("게시글 목록을 검색한다.")
    @Test
    void findPosts() throws Exception {
        // given
        Member member1 = Member.create("aaa", "1234", "AAA");
        Member member2 = Member.create("bbb", "1234", "BBB");

        Post post1 = Post.create("제목11", "내용11", member1);
        Post post2 = Post.create("제목12", "내용12", member1);
        Post post3 = Post.create("제목23", "내용23", member2);
        Post post4 = Post.create("제목24", "내용34", member2);

        BDDMockito.given(postService.findPosts(any(PostSearchRequest.class), any(Pageable.class)))
                        .willReturn(new PageImpl<>(List.of(PostResponse.from(post1), PostResponse.from(post2), PostResponse.from(post3))));

        MockHttpSession mockHttpSession = new MockHttpSession();
        mockHttpSession.setAttribute("loginMember", "abc");

        // when // then
        mockMvc.perform(
                        get("/posts").session(mockHttpSession)
                                .queryParam("searchCode","TITLE")
                                .queryParam( "searchString", "1")
                )
                .andExpect(status().isOk());
    }
}