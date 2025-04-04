package groupProject.exception;

import java.util.UUID;

public class CourseMaterialNotFound extends Exception {
  public CourseMaterialNotFound(UUID id) {
    super("CourseMaterial " + id + " does not exist.");
  }
}
