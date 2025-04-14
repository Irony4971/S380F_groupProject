package groupProject.controller;

import groupProject.dao.LectureNoteUserRepository;
import groupProject.model.LectureNoteUser;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/updateProfile")
public class ProfileController {

    private final LectureNoteUserRepository userRepo;

    public ProfileController(LectureNoteUserRepository userRepo) {
        this.userRepo = userRepo;
    }

    @GetMapping
    public String showProfile(Model model) {
        String username = getCurrentUsername();
        model.addAttribute("user", userRepo.findById(username).orElseThrow());
        return "profile";
    }

    @PostMapping
    @Transactional
    public String updateProfile(
            @ModelAttribute("user") LectureNoteUser updatedUser,
            @RequestParam(required = false) String newPassword) {

        String username = getCurrentUsername();
        LectureNoteUser user = userRepo.findById(username).orElseThrow();

        user.setFullName(updatedUser.getFullName());
        user.setEmail(updatedUser.getEmail());
        user.setPhone(updatedUser.getPhone());

        if (newPassword != null && !newPassword.trim().isEmpty()) {
            user.setPassword("{noop}" + newPassword);
        }

        userRepo.save(user);
        return "redirect:/course380F?updated";
    }

    private String getCurrentUsername() {
        return SecurityContextHolder.getContext().getAuthentication().getName();
    }
}
