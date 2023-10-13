package bulletin.board.service;

import bulletin.board.constant.ErrorCode;
import bulletin.board.domain.Post;
import bulletin.board.domain.UploadFile;
import bulletin.board.exceptions.EntityNotFoundException;
import bulletin.board.exceptions.FileUploadException;
import bulletin.board.repository.UploadFileRepository;
import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.WriteResult;
import com.google.cloud.storage.Storage;
import com.google.firebase.cloud.FirestoreClient;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.core.io.ByteArrayResource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.*;

import com.google.cloud.storage.Bucket;

@Slf4j
@Service
@RequiredArgsConstructor
public class UploadFileService {

    private final UploadFileRepository uploadFileRepository;
    private final Bucket bucket;

    private String getFullPath(Long postId, String filename) {
        return "/posts/" + postId + "/images/" + filename;
    }

    @Transactional
    public void storeFiles(List<MultipartFile> multipartFiles, Post post) {
        multipartFiles.forEach(multipartFile -> storeFile(multipartFile, post));
    }

    @Transactional
    public void storeFile(MultipartFile multipartFile, Post post) {
        if (multipartFile.isEmpty()) {
            return;
        }

        String originalFilename = multipartFile.getOriginalFilename();
        String storeFileName = createStoreFileName(originalFilename);
        String blob = getFullPath(post.getId(), storeFileName);

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

    private String createStoreFileName(String originalFilename) {
        String ext = extractExt(originalFilename);
        String uuid = UUID.randomUUID().toString();

        return uuid + "." + ext;
    }

    private String extractExt(String originalFilename) {
        int pos = originalFilename.lastIndexOf(".");
        return originalFilename.substring(pos + 1);
    }

    public ByteArrayResource findImage(Long imageId) {
        UploadFile image = uploadFileRepository.findById(imageId)
                .orElseThrow(() -> new EntityNotFoundException(ErrorCode.IMAGE_NOT_FOUND));

        byte[] content = bucket.get(image.getPath()).getContent();

        return new ByteArrayResource(content);
    }

    @Transactional
    public void delete(Long imageId) {
        UploadFile image = uploadFileRepository.findById(imageId)
                .orElseThrow(() -> new EntityNotFoundException(ErrorCode.IMAGE_NOT_FOUND));

        bucket.get(image.getPath()).delete();

        uploadFileRepository.delete(image);
    }

    @Transactional
    public void updateFiles(List<MultipartFile> images, Post post) {
        if (images != null) {
            storeFiles(images, post);
        }
    }

    @Transactional
    public void deleteFiles(List<Long> deleteImageIds) {
        if (deleteImageIds != null) {
            deleteImageIds.forEach(this::delete);
        }
    }
}
