package groupProject.dao;

import groupProject.model.LectureNoteUser;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LectureNoteUserRepository extends JpaRepository<LectureNoteUser, String> {
}
