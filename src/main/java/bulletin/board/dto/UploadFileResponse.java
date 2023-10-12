package bulletin.board.dto;

import bulletin.board.constant.ErrorCode;
import bulletin.board.domain.UploadFile;
import bulletin.board.exceptions.WrongPathException;
import lombok.Builder;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.UrlResource;

import java.net.MalformedURLException;
import java.util.List;
import java.util.stream.Collectors;
@Slf4j
@Getter
@Builder
public class UploadFileResponse {

    private String name;
    private UrlResource url;

    public static List<UploadFileResponse> of(List<UploadFile> imageFiles) {
        log.info("사이즈 = {}", imageFiles.size());

        return imageFiles.stream()
                .peek(uploadFile -> log.info("image {}, {}" ,uploadFile.getUploadFileName(), uploadFile.getPath()))
                .map(UploadFileResponse::of)
                .peek(r -> log.info("r {}, {}" ,r.getName(), r.getUrl()))
                .collect(Collectors.toList());
    }

    public static UploadFileResponse of(UploadFile imageFiles) {

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
