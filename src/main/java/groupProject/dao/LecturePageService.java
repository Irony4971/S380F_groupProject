package groupProject.dao;

import groupProject.exception.LecturePageNotFound;
import groupProject.model.LectureNote;
import groupProject.model.LecturePage;
import jakarta.annotation.Resource;
import org.hibernate.Hibernate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
public class LecturePageService {
    @Resource
    private LecturePageRepository lecturePageRepo;

    @Transactional
    public List<LecturePage> getAllLecturePages() {
        return lecturePageRepo.findAll();
    }

    @Resource
    private LectureNoteRepository lectureNoteRepo;

    @Transactional
    public LecturePage getLecturePageById(Long id) throws LecturePageNotFound {
        return lecturePageRepo.findById(id)
                .orElseThrow(() -> new LecturePageNotFound(id));
    }
    @Transactional
    public LecturePage getLecturePageWithNotes(Long pageId) throws LecturePageNotFound {
        LecturePage page = lecturePageRepo.findById(pageId)
                .orElseThrow(() -> new LecturePageNotFound(pageId));

        Hibernate.initialize(page.getLectureNotes());
        for (LectureNote note : page.getLectureNotes()) {
            Hibernate.initialize(note.getCourseMaterials());
        }

        return page;
    }

    @Transactional
    public LecturePage createLecturePage(String title, String description) {
        LecturePage lecturePage = new LecturePage();
        lecturePage.setTitle(title);
        lecturePage.setDescription(description);
        return lecturePageRepo.save(lecturePage);
    }

    @Transactional
    public void deleteLecturePage(Long id) throws LecturePageNotFound {
        LecturePage lecturePage = getLecturePageById(id);
        lecturePageRepo.delete(lecturePage);
    }
}
