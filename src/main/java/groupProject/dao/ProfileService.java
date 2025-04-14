package groupProject.dao;

import groupProject.model.LectureNoteUser;
import jakarta.annotation.Resource;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

@Service
public class ProfileService {

    @Resource
    private LectureNoteUserRepository userRepo;

    public ProfileService(LectureNoteUserRepository userRepo) {
        this.userRepo = userRepo;
    }

    @Transactional
    public LectureNoteUser getCurrentUser(String username) {
        return userRepo.findById(username).orElseThrow();
    }

    @Transactional
    public void updateProfile(String username, LectureNoteUser updatedUser, String newPassword) {
        LectureNoteUser user = userRepo.findById(username).orElseThrow();

        user.setFullName(updatedUser.getFullName());
        user.setEmail(updatedUser.getEmail());
        user.setPhone(updatedUser.getPhone());

        if (newPassword != null && !newPassword.trim().isEmpty()) {
            user.setPassword(newPassword);
        }
        userRepo.save(user);
    }
}