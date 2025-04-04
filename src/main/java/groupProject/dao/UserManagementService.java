package groupProject.dao;

import groupProject.model.LectureNoteUser;
import jakarta.annotation.Resource;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class UserManagementService {
    @Resource
    private LectureNoteUserRepository tuRepo;

    @Transactional
    public List<LectureNoteUser> getLectureNoteUsers() {
        return tuRepo.findAll();
    }

    @Transactional
    public void delete(String username) {
        LectureNoteUser lectureNoteUser = tuRepo.findById(username).orElse(null);
        if (lectureNoteUser == null) {
            throw new UsernameNotFoundException("User '" + username + "' not found.");
        }
        tuRepo.delete(lectureNoteUser);
    }

    @Transactional
    public void createLectureNoteUser(String username, String password, String[] roles) {
        LectureNoteUser user = new LectureNoteUser(username, password, roles);
        tuRepo.save(user);
    }
}
