package groupProject.dao;

import groupProject.model.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    List<Comment> findByLecturePageId(Long lecturePageId);
    List<Comment> findByUsernameOrderByDateDesc(String username);
}
