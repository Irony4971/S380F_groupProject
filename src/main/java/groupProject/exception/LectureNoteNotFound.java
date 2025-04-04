package groupProject.exception;

public class LectureNoteNotFound extends Exception {
    public LectureNoteNotFound(long id) {
        super("LectureNote " + id + " does not exist.");
    }
}
