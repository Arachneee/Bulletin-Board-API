package bulletin.board.api.service.post.response;

import bulletin.board.domain.post.UploadFile;
import bulletin.board.exceptions.WrongPathException;
import bulletin.board.exceptions.constant.ErrorCode;
import lombok.Builder;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.UrlResource;

import java.net.MalformedURLException;
import java.util.List;
@Slf4j
@Getter
@Builder
public class UploadFileResponse {

    private String name;
    private UrlResource url;

    public static List<UploadFileResponse> from(final List<UploadFile> imageFiles) {
        log.info("사이즈 = {}", imageFiles.size());

        return imageFiles.stream()
                .peek(uploadFile -> log.info("image {}, {}" ,uploadFile.getUploadFileName(), uploadFile.getPath()))
                .map(UploadFileResponse::from)
                .peek(r -> log.info("r {}, {}" ,r.getName(), r.getUrl()))
                .toList();
    }

    public static UploadFileResponse from(final UploadFile imageFiles) {
        try {
            return UploadFileResponse.builder()
                    .name(imageFiles.getUploadFileName())
                    .url(new UrlResource("file:" + imageFiles.getPath()))
                    .build();
        } catch (MalformedURLException e) {
            throw new WrongPathException(ErrorCode.BAD_URL);
        }
    }
}
