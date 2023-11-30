package bulletin.board.api.service.post;

import bulletin.board.domain.post.Post;
import bulletin.board.domain.post.UploadFile;
import bulletin.board.domain.post.repository.UploadFileRepository;
import bulletin.board.exceptions.EntityNotFoundException;
import bulletin.board.exceptions.FileUploadException;
import bulletin.board.exceptions.constant.ErrorCode;
import com.google.cloud.storage.Bucket;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class UploadFileService {

    private final UploadFileRepository uploadFileRepository;
    private final Bucket bucket;

    private String getFullPath(final Long postId, final String filename) {
        return "/posts/" + postId + "/images/" + filename;
    }

    @Transactional
    public void storeFiles(final List<MultipartFile> multipartFiles, final Post post) {
        if (multipartFiles != null) {
            multipartFiles.forEach(multipartFile -> storeFile(multipartFile, post));
        }
    }

    @Transactional
    public void storeFile(final MultipartFile multipartFile, final Post post) {
        if (multipartFile.isEmpty()) {
            return;
        }

        final String originalFilename = multipartFile.getOriginalFilename();
        final String storeFileName = createStoreFileName(originalFilename);
        final String blob = getFullPath(post.getId(), storeFileName);

        try {
            if(bucket.get(blob) != null) {
                bucket.get(blob).delete();
            }
            byte[] image = multipartFile.getBytes();

            bucket.create(blob, image);
            uploadFileRepository.save(UploadFile.create(originalFilename, storeFileName, blob, post));
        } catch (IOException e) {
            throw new FileUploadException(ErrorCode.FILE_NOT_UPLOAD);
        }
    }

    private String createStoreFileName(final String originalFilename) {
        return UUID.randomUUID() + "." + extractExt(originalFilename);
    }

    private String extractExt(final String originalFilename) {
        final int pos = originalFilename.lastIndexOf(".");
        return originalFilename.substring(pos + 1);
    }

    public ByteArrayResource findImage(final Long imageId) {
        final UploadFile image = uploadFileRepository.findById(imageId)
                .orElseThrow(() -> new EntityNotFoundException(ErrorCode.IMAGE_NOT_FOUND));

        final byte[] content = bucket.get(image.getPath()).getContent();

        return new ByteArrayResource(content);
    }

    @Transactional
    public void updateFiles(final List<MultipartFile> images, final Post post) {
        if (images != null) {
            storeFiles(images, post);
        }
    }

    @Transactional
    public void delete(final Long imageId) {
        final UploadFile image = uploadFileRepository.findById(imageId)
                .orElseThrow(() -> new EntityNotFoundException(ErrorCode.IMAGE_NOT_FOUND));

        bucket.get(image.getPath()).delete();

        uploadFileRepository.delete(image);
    }

    @Transactional
    public void deleteFiles(final List<Long> deleteImageIds) {
        if (deleteImageIds != null) {
            deleteImageIds.forEach(this::delete);
        }
    }
}
