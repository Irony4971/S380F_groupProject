package groupProject.model;

import jakarta.persistence.*;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import java.util.ArrayList;
import java.util.List;

@Entity
public class LectureNote {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "name")
    private String userName;
    private String subject;
    private String body;

    @ManyToOne
    @JoinColumn(name = "lecture_page_id")
    private LecturePage lecturePage;

    @OneToMany(mappedBy = "lectureNote", fetch = FetchType.EAGER,
            cascade = CascadeType.ALL, orphanRemoval = true)
    @Fetch(FetchMode.SUBSELECT)
    private List<CourseMaterial> courseMaterials = new ArrayList<>();

    public LecturePage getLecturePage() {
        return lecturePage;
    }

    public void setLecturePage(LecturePage lecturePage) {
        this.lecturePage = lecturePage;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public List<CourseMaterial> getCourseMaterials() {
        return courseMaterials;
    }

    public void setCourseMaterials(List<CourseMaterial> courseMaterials) {
        this.courseMaterials = courseMaterials;
    }

    public void deleteCourseMaterial(CourseMaterial courseMaterial) {
        courseMaterial.setLectureNote(null);
        this.courseMaterials.remove(courseMaterial);
    }
}
