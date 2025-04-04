package groupProject.dao;

import groupProject.model.CourseMaterial;
import groupProject.model.LectureNote;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface LectureNoteRepository extends JpaRepository<LectureNote, Long> {
    List<LectureNote> findByUserName(String userName);
    List<LectureNote> findByLecturePageId(Long lecturePageId);
}
