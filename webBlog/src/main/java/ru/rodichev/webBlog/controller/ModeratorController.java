package ru.rodichev.webBlog.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.rodichev.webBlog.entity.Note;
import ru.rodichev.webBlog.repo.NotesRepository;
import ru.rodichev.webBlog.service.NotesService;

@Controller
public class ModeratorController {

    @Autowired
    private NotesService notesService;

    @GetMapping("/moderator")
    public String moderatorPage(Model model) {
        model.addAttribute("notes", notesService.getOnlyUnchecked());
        return "moderator/main";
    }

    @GetMapping("/moderate/{id}")
    public String moderDetails(@PathVariable("id") Long id, Model model) {
        Note note = notesService.getNoteById(id);
        if (!note.isChecked()) {
            model.addAttribute("note", note);
        } else model.addAttribute("msg", "this note already checked");
        return "moderator/details";
    }

    @PostMapping("/moderate/{id}")
    public String uploadRemarks(@PathVariable("id") Long id, @RequestParam String allRemarks, Model model) {
        Note note = notesService.getNoteById(id);
        if (allRemarks == "") {
            note.setFixed(true);
            note.setFinalFullText(note.getRawFullText());
        } else note.setModerateFullText(allRemarks);
        note.setChecked(true);
        notesService.saveNote(note);
        return "redirect:/moderator";
    }

}
