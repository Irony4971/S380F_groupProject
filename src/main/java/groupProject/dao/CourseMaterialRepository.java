package groupProject.dao;

import groupProject.model.CourseMaterial;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface CourseMaterialRepository extends JpaRepository<CourseMaterial, UUID> {
}
