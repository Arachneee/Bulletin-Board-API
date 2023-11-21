package bulletin.board.domain.post;

import bulletin.board.domain.BaseEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import static jakarta.persistence.FetchType.LAZY;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class UploadFile extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "file_id")
    private Long id;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "post_id")
    private Post post;

    @NotEmpty
    @Column(nullable = false, length = 100)
    private String uploadFileName;

    @NotEmpty
    private String storeFileName;

    @NotEmpty
    private String path;

    public static UploadFile create(String uploadFileName, String storeFileName, String path, Post post) {
        UploadFile uploadFile = new UploadFile();
        uploadFile.setStoreFileName(storeFileName);
        uploadFile.setUploadFileName(uploadFileName);
        uploadFile.setPath(path);
        uploadFile.setPost(post);

        return uploadFile;
    }

    private void setPost(Post post) {
        this.post = post;
        post.getImages().add(this);
    }

    private void setPath(String path) {
        this.path = path;
    }

    private void setUploadFileName(String uploadFileName) {
        this.uploadFileName = uploadFileName;
    }

    private void setStoreFileName(String storeFileName) {
        this.storeFileName = storeFileName;
    }

    public String getImageUrl() {
        return "/posts/" + getPost().getId() + "/images/" + getId();
    }
}
