package groupProject.exception;

public class LecturePageNotFound extends Exception {
  public LecturePageNotFound(Long id) {
    super("Lecture page not found with ID: " + id);
  }
}