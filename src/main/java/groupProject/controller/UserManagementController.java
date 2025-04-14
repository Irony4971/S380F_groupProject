package groupProject.controller;

import groupProject.dao.UserManagementService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.io.IOException;

@Controller
@RequestMapping("/user")
public class UserManagementController {
    @Resource
    UserManagementService umService;

    @GetMapping({"", "/", "/index"})
    public String list(ModelMap model) {
        model.addAttribute("lectureNoteUsers", umService.getLectureNoteUsers());
        return "UserManagement";
    }

    public static class Form {
        private String username;
        private String password;
        private String[] roles;
        private String fullName;
        private String email;
        private String phone;


        // getters and setters for all properties
        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }

        public String[] getRoles() {
            return roles;
        }

        public void setRoles(String[] roles) {
            this.roles = roles;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public String getFullName() {
            return fullName;
        }

        public void setFullName(String fullName) {
            this.fullName = fullName;
        }

        public String getPhone() {
            return phone;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }
    }

    @GetMapping("/create")
    public ModelAndView create() {
        return new ModelAndView("addUser", "lectureNoteUser", new Form());
    }

    @PostMapping("/create")
    public String create(Form form) throws IOException {
        umService.createLectureNoteUser(form.getUsername(), form.getPassword(), form.getRoles(),form.getFullName(),
                form.getEmail(),form.getPhone());
        return "redirect:/user";
    }
    @GetMapping("/delete/{username}")
    public String deleteLectureNote(@PathVariable("username") String username) {
        umService.delete(username);
        return "redirect:/user";
    }
}
