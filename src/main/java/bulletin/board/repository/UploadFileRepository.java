package bulletin.board.repository;

import bulletin.board.domain.UploadFile;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UploadFileRepository extends JpaRepository<UploadFile, Long> {
    List<UploadFile> findByPostId(Long postId);
}
