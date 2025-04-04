package groupProject.model;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class LecturePage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;
    private String description;

    @OneToMany(mappedBy = "lecturePage", cascade = CascadeType.ALL)
    private List<LectureNote> lectureNotes = new ArrayList<>();

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<LectureNote> getLectureNotes() {
        return lectureNotes;
    }

    public void setLectureNotes(List<LectureNote> lectureNotes) {
        this.lectureNotes = lectureNotes;
    }

    public void addLectureNote(LectureNote lectureNote) {
        lectureNotes.add(lectureNote);
        lectureNote.setLecturePage(this);
    }

    public void removeLectureNote(LectureNote lectureNote) {
        lectureNotes.remove(lectureNote);
        lectureNote.setLecturePage(null);
    }
}