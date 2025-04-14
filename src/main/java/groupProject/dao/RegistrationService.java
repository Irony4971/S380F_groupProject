package groupProject.dao;

import groupProject.model.LectureNoteUser;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class RegistrationService {

    @Resource
    private LectureNoteUserRepository userRepository;

    @Transactional
    public void registerNewUser(String username, String password, String[] roles,
                                String fullName, String email, String phone) {
        if (userRepository.existsById(username)) {
            throw new IllegalArgumentException("Username '" + username + "' already exists");
        }

        LectureNoteUser newUser = new LectureNoteUser(
                username,
                "{noop}" + password,
                roles,
                fullName,
                email,
                phone
        );

        userRepository.save(newUser);

        LectureNoteUser savedUser = userRepository.findById(username).orElse(null);
        if (savedUser == null) {
            System.err.println("ERROR: User was not saved to database!");
        } else {
            System.out.println("User successfully saved: " + savedUser);
        }
    }
}