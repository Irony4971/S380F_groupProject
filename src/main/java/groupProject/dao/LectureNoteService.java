package groupProject.dao;

import groupProject.exception.CourseMaterialNotFound;
import groupProject.exception.LectureNoteNotFound;
import groupProject.exception.LecturePageNotFound;
import groupProject.model.CourseMaterial;
import groupProject.model.LectureNote;
import groupProject.model.LecturePage;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

@Service
public class LectureNoteService {
    @Resource
    private LectureNoteRepository tRepo;

    @Resource
    private CourseMaterialRepository aRepo;

    @Resource
    private LecturePageRepository lecturePageRepository;

    @Transactional
    public List<LectureNote> getLectureNotes() {
        return tRepo.findAll();
    }

    @Transactional
    public void saveLectureNote(LectureNote lectureNote) {
        tRepo.save(lectureNote);
    }

    @Transactional
    public LectureNote getLectureNote(long id)
            throws LectureNoteNotFound {
        LectureNote lectureNote = tRepo.findById(id).orElse(null);
        if (lectureNote == null) {
            throw new LectureNoteNotFound(id);
        }
        return lectureNote;
    }

    @Transactional
    public CourseMaterial getCourseMaterial(UUID materialId)
            throws CourseMaterialNotFound {
        CourseMaterial material = aRepo.findById(materialId).orElse(null);
        if (material == null) {
            throw new CourseMaterialNotFound(materialId);
        }
        return material;
    }

    @Transactional
    public void deleteNoteWithMaterials(Long noteId) throws LectureNoteNotFound {
        LectureNote note = tRepo.findById(noteId)
                .orElseThrow(() -> new LectureNoteNotFound(noteId));
        System.out.println("Found note: " + note.getId());

        if (!note.getCourseMaterials().isEmpty()) {
            System.out.println("Deleting " + note.getCourseMaterials().size() + " materials");
            aRepo.deleteAll(note.getCourseMaterials());
        }

        tRepo.delete(note);
        System.out.println("Note deleted successfully");
    }

    @Transactional(rollbackFor = CourseMaterialNotFound.class)
    public void deleteCourseMaterial(long lectureNoteId, UUID courseMaterialId)
            throws LectureNoteNotFound, CourseMaterialNotFound {
        LectureNote lectureNote = tRepo.findById(lectureNoteId).orElse(null);
        if (lectureNote == null) {
            throw new LectureNoteNotFound(lectureNoteId);
        }
        for (CourseMaterial courseMaterial : lectureNote.getCourseMaterials()) {
            if (courseMaterial.getId().equals(courseMaterialId)) {
                lectureNote.deleteCourseMaterial(courseMaterial);
                tRepo.save(lectureNote);
                return;
            }
        }
        throw new CourseMaterialNotFound(courseMaterialId);
    }


    @Transactional
    public long createLectureNote(String userName, String subject,
                                  String body, List<MultipartFile> courseMaterials, Long pageId)
            throws IOException, LecturePageNotFound {
        LecturePage lecturePage = lecturePageRepository.findById(pageId)
                .orElseThrow(() -> new LecturePageNotFound(pageId));

        LectureNote lectureNote = new LectureNote();
        lectureNote.setUserName(userName);
        lectureNote.setSubject(subject);
        lectureNote.setBody(body);
        lectureNote.setLecturePage(lecturePage);

        if (courseMaterials != null) {
            for (MultipartFile file : courseMaterials) {
                if (!file.isEmpty()) {
                    CourseMaterial material = new CourseMaterial();
                    material.setName(file.getOriginalFilename());
                    material.setMimeContentType(file.getContentType());
                    material.setContents(file.getBytes());
                    material.setLectureNote(lectureNote);
                    lectureNote.getCourseMaterials().add(material);
                }
            }
        }

        LectureNote savedNote = tRepo.save(lectureNote);

        lecturePage.getLectureNotes().add(savedNote);
        lecturePageRepository.save(lecturePage);

        return savedNote.getId();
    }

    @Transactional
    public List<LectureNote> getLectureNotesByUser(String userName) {
        return tRepo.findByUserName(userName);
    }

    @Transactional(rollbackFor = LectureNoteNotFound.class)
    public void updateLectureNote(long id, String subject,
                             String body, List<MultipartFile> courseMaterials)
            throws IOException, LectureNoteNotFound {
        LectureNote updatedLectureNote = tRepo.findById(id).orElse(null);
        if (updatedLectureNote == null) {
            throw new LectureNoteNotFound(id);
        }
        updatedLectureNote.setSubject(subject);
        updatedLectureNote.setBody(body);
        for (MultipartFile filePart : courseMaterials) {
            CourseMaterial courseMaterial = new CourseMaterial();
            courseMaterial.setName(filePart.getOriginalFilename());
            courseMaterial.setMimeContentType(filePart.getContentType());
            courseMaterial.setContents(filePart.getBytes());
            courseMaterial.setLectureNote(updatedLectureNote);
            if (courseMaterial.getName() != null && courseMaterial.getName().length() > 0
                    && courseMaterial.getContents() != null
                    && courseMaterial.getContents().length > 0) {
                updatedLectureNote.getCourseMaterials().add(courseMaterial);
            }
        }
        tRepo.save(updatedLectureNote);
    }
}
