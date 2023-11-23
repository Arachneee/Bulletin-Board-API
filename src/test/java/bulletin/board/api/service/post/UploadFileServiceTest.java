package bulletin.board.api.service.post;

import bulletin.board.domain.member.Member;
import bulletin.board.domain.post.Post;
import bulletin.board.domain.post.UploadFile;
import bulletin.board.domain.post.repository.UploadFileRepository;
import com.google.cloud.storage.Blob;
import com.google.cloud.storage.Bucket;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.transaction.annotation.Transactional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@Transactional
@ExtendWith(MockitoExtension.class)
class UploadFileServiceTest {

    @Mock
    private Bucket bucket;
    @Mock
    private UploadFileRepository uploadFileRepository;

    @InjectMocks
    private UploadFileService uploadFileService;

    @DisplayName("파일을 저장할 수 있다.")
    @Test
    void storeFile() {
        // given
        Member member = Member.create("aaa", "1234", "AAA");
        Post post = Post.create("제목1", "내용1", member);

        MockMultipartFile image = new MockMultipartFile("이름", "원본이름.jpg", "image", new byte[]{1, 1, 1, 1});
        BDDMockito.given(bucket.create(anyString(), any(byte[].class)))
                .willReturn(any(Blob.class));

        // when
        uploadFileService.storeFile(image, post);

        // then
        verify(uploadFileRepository, times(1)).save(any(UploadFile.class));
    }


}