package groupProject.controller;

import groupProject.dao.RegistrationService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;


@Controller
@RequestMapping("/register")
public class RegistrationController {

    @Resource
    private RegistrationService registrationService;

    @GetMapping
    public ModelAndView registerForm() {
        return new ModelAndView("registration", "user", new UserManagementController.Form());
    }

    @PostMapping
    public String processRegistration(UserManagementController.Form form) {
        try {
            // Default role for new registrations
            String[] roles = {"ROLE_STUDENT"};

            registrationService.registerNewUser(
                    form.getUsername(),
                    form.getPassword(),
                    roles,
                    form.getFullName(),
                    form.getEmail(),
                    form.getPhone()
            );
            return "redirect:/login?registered";
        } catch (IllegalArgumentException e) {
            return "redirect:/register?error=username_taken";
        }
    }
}