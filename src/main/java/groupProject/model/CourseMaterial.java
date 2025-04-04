package groupProject.model;

import jakarta.persistence.*;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.GenericGenerator;

import java.util.UUID;

@Entity
public class CourseMaterial {
    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(
            name = "UUID",
            strategy = "org.hibernate.id.UUIDGenerator"
    )
    @Column(columnDefinition = "BINARY(16)")
    private UUID id;

    private String name;
    private String mimeContentType;

    @Lob
    @Basic(fetch = FetchType.LAZY)
    private byte[] contents;


    @ManyToOne
    @JoinColumn(name = "lectureNote_id")
    private LectureNote lectureNote;

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMimeContentType() {
        return mimeContentType;
    }

    public void setMimeContentType(String mimeContentType) {
        this.mimeContentType = mimeContentType;
    }

    public byte[] getContents() {
        return contents;
    }

    public void setContents(byte[] contents) {
        this.contents = contents;
    }

    public LectureNote getLectureNote() {
        return lectureNote;
    }

    public void setLectureNote(LectureNote lectureNote) {
        this.lectureNote = lectureNote;
    }
}


