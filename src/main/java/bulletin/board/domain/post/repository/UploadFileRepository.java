package bulletin.board.domain.post.repository;

import bulletin.board.domain.post.UploadFile;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UploadFileRepository extends JpaRepository<UploadFile, Long> {
    List<UploadFile> findByPostId(Long postId);
}
