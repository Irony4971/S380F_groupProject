package groupProject.controller;

import groupProject.dao.LecturePageService;
import groupProject.dao.LectureNoteService;
import groupProject.exception.*;
import groupProject.model.*;
import groupProject.view.DownloadingView;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.View;
import org.springframework.web.servlet.view.RedirectView;
import jakarta.annotation.Resource;


import java.io.IOException;
import java.security.Principal;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

@Controller
@RequestMapping("/course380F")
public class CourseController {

    @Resource
    private LecturePageService lecturePageService;

    @Resource
    private LectureNoteService lectureNoteService;

    @GetMapping("")
    public String index(ModelMap model, Principal principal) {
        model.addAttribute("lecturePages", lecturePageService.getAllLecturePages());
        return "index";
    }

    @GetMapping("/createPage")
    public String showCreatePageForm() {
        return "createPage";
    }

    @PostMapping("/createPage")
    public View createPage(@RequestParam String title,
                           @RequestParam String description) {
        LecturePage page = lecturePageService.createLecturePage(title, description);
        return new RedirectView("/course380F/lecture/" + page.getId(), true);
    }

    @GetMapping("/deletePage/{pageId}")
    public View deletePage(@PathVariable Long pageId) throws LecturePageNotFound {
        lecturePageService.deleteLecturePage(pageId);
        return new RedirectView("/course380F", true);
    }

    @GetMapping("/lecture/{pageId}")
    @Transactional
    public String LecturePage(@PathVariable Long pageId, ModelMap model)
            throws LecturePageNotFound {
        LecturePage lecturePage = lecturePageService.getLecturePageWithNotes(pageId);
        model.addAttribute("lecturePage", lecturePage);
        return "lecturePage";
    }

    @GetMapping("/lecture/{pageId}/create")
    public ModelAndView showCreateForm(@PathVariable Long pageId) {
        ModelAndView model = new ModelAndView("add");
        model.addObject("lectureNoteForm", new Form());
        model.addObject("pageId", pageId);
        return model;
    }
    public static class Form {
        private String subject;
        private String body;
        private List<MultipartFile> courseMaterials;

        public String getSubject() { return subject; }
        public void setSubject(String subject) { this.subject = subject; }
        public String getBody() { return body; }
        public void setBody(String body) { this.body = body; }
        public List<MultipartFile> getCourseMaterials() { return courseMaterials; }
        public void setCourseMaterials(List<MultipartFile> courseMaterials) { this.courseMaterials = courseMaterials; }
    }

    @PostMapping("/lecture/{pageId}/create")
    public String createNote(@PathVariable Long pageId,
                             @ModelAttribute Form form,
                             Principal principal) throws IOException, LecturePageNotFound {
        List<MultipartFile> files = form.getCourseMaterials() != null ?
                form.getCourseMaterials() : Collections.emptyList();

        long noteId = lectureNoteService.createLectureNote(
                principal.getName(),
                form.getSubject(),
                form.getBody(),
                files,
                pageId
        );

        return "redirect:/course380F/lecture/" + pageId + "?t=" + System.currentTimeMillis();
    }

    @GetMapping("/lecture/{pageId}/edit/{noteId}")
    public ModelAndView showEditForm(@PathVariable Long pageId,
                                     @PathVariable Long noteId,
                                     Principal principal,
                                     HttpServletRequest request) throws LectureNoteNotFound {
        LectureNote note = lectureNoteService.getLectureNote(noteId);

        if (!request.isUserInRole("ROLE_TEACHER")
                && !principal.getName().equals(note.getUserName())) {
            return new ModelAndView("redirect:/course380F/lecture/" + pageId);
        }

        ModelAndView model = new ModelAndView("edit");
        Form form = new Form();
        form.setSubject(note.getSubject());
        form.setBody(note.getBody());
        model.addObject("lectureNoteForm", form);
        model.addObject("pageId", pageId);
        return model;
    }

    @PostMapping("/lecture/{pageId}/edit/{noteId}")
    public String updateNote(@PathVariable Long pageId,
                             @PathVariable Long noteId,
                             @ModelAttribute Form form,
                             Principal principal,
                             HttpServletRequest request) throws LectureNoteNotFound, IOException {
        LectureNote note = lectureNoteService.getLectureNote(noteId);

        if (!request.isUserInRole("ROLE_TEACHER")
                && !principal.getName().equals(note.getUserName())) {
            return "redirect:/course380F/lecture/" + pageId;
        }

        lectureNoteService.updateLectureNote(
                noteId,
                form.getSubject(),
                form.getBody(),
                form.getCourseMaterials()
        );
        return "redirect:/course380F/lecture/" + pageId ;
    }
    @PostMapping("/lecture/{pageId}/delete/{noteId}")
    public String deleteNoteWithMaterials(
            @PathVariable Long pageId,
            @PathVariable Long noteId,
            HttpServletRequest request) {;
        try {
            System.out.println("Deleting note " + noteId + " from page " + pageId);
            lectureNoteService.deleteNoteWithMaterials(noteId);
            return "redirect:/course380F/lecture/" + pageId;
        } catch (LectureNoteNotFound e) {
            return "redirect:/course380F/lecture/" + pageId + "?error=Note+not+found";
        }
    }


    @GetMapping("/lecture/{pageId}/material/{materialId}")
    public View downloadMaterial(@PathVariable UUID materialId)
            throws CourseMaterialNotFound {
        CourseMaterial material = lectureNoteService.getCourseMaterial(materialId);
        if (material == null || material.getContents() == null) {
            throw new CourseMaterialNotFound(materialId);
        }
        return new DownloadingView(
                material.getName(),
                material.getMimeContentType(),
                material.getContents()
        );
    }

    @GetMapping("/lecture/{pageId}/deleteMaterial/{materialId:.+}")
    public String deleteMaterial(@PathVariable Long pageId,
                                 @PathVariable Long noteId,
                                 @PathVariable UUID materialId)
            throws LectureNoteNotFound, CourseMaterialNotFound {
        lectureNoteService.deleteCourseMaterial(noteId, materialId);
        return "redirect:/course380F/lecture/" + pageId ;
    }
    @ExceptionHandler({LectureNoteNotFound.class, CourseMaterialNotFound.class, LecturePageNotFound.class})
    public ModelAndView error(Exception e) {
        return new ModelAndView("error", "message", e.getMessage());
    }
}
