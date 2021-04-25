package ru.rodichev.webBlog.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.rodichev.webBlog.entity.Note;
import ru.rodichev.webBlog.logic.Remark;
import ru.rodichev.webBlog.repo.NotesRepository;
import ru.rodichev.webBlog.service.NotesService;

@Controller
public class SupertrampController {

//    @Autowired
//    private NotesRepository notesRepository;

    @Autowired
    private NotesService notesService;

    @GetMapping("/supertramp/fix")
    public String goToFixList(Model model) {
        model.addAttribute("fixList", notesService.getOnlyUnfixed());
        return "supertramp/fixMain";
    }

    @GetMapping("/supertramp/fix/{id}")
    public String goToFixNote(@PathVariable("id") Long id, Model model) {
        Note note = notesService.getNoteById(id);
        String noteWithRemarks = Remark.getPopupText(note.getRawFullText(), Remark.getSortRemarks(note.getModerateFullText()));
        model.addAttribute("fullText", noteWithRemarks);
        model.addAttribute("note", note);
        return "supertramp/fixDetails";
    }

    @PostMapping("/supertramp/fix/{id}")
    public String UploadFixes(@PathVariable("id") Long id, @RequestParam String fullNewText, Model model) {
        Note note = notesService.getNoteById(id);
        note.setFinalFullText(Note.toHtmlBreakLines(fullNewText));
        note.setFixed(true);
        notesService.saveNote(note);
        model.addAttribute("fixList", notesService.getOnlyUnfixed());
        return "supertramp/fixMain";
    }
}
