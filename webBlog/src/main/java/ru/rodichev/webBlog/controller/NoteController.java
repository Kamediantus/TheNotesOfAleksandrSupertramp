package ru.rodichev.webBlog.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.rodichev.webBlog.entity.Comment;
import ru.rodichev.webBlog.logic.CurrDate;
import ru.rodichev.webBlog.entity.Note;
import ru.rodichev.webBlog.logic.SearchFromRepo;
import ru.rodichev.webBlog.repo.CommentRepository;
import ru.rodichev.webBlog.repo.NotesRepository;
import ru.rodichev.webBlog.service.NotesService;

import java.util.*;

@Controller
public class NoteController {

    @Autowired
    private NotesService notesService;

//    @Autowired
//    private NotesRepository notesRepository;

    @Autowired
    private CommentRepository commentRepository;

    @GetMapping("/notes")
    public String blog(Model model) {
        Iterable<Note> notes = notesService.getOnlyChecked();
        model.addAttribute("notes", notes);
        return "note/mainNotes";
    }

    @PostMapping("/notes")
    public String searchNotes(@RequestParam String text, @RequestParam String tag, Model model) {
        List<Note> notes = null;
        String msg;
        if (text != "") {
            notes = notesService.reverseFindByText(text);
            model.addAttribute("notes", notes);
            if (notes.size() == 0){
                model.addAttribute("msg", "nothing was found");
            }
        } else if (tag != "") {
            notes = notesService.reverseFindByTag(tag);
            model.addAttribute("notes", notes);
            if (notes.size() == 0){
                model.addAttribute("msg", "nothing was found");
            }
        } else {
            model.addAttribute("msg", "Please choose at least one parameter for search");
        }
        return "note/mainNotes";
    }

    @GetMapping("/notes/add")
    public String addNote(Model model) {
        return "note/newNote";
    }

    @PostMapping("/notes/add")
    public String submitNewNote(@RequestParam String heading, @RequestParam String fullText, @RequestParam String tags, Model model) {
        Note note = new Note(heading, fullText, tags);
        notesService.saveNote(note);
        return "redirect:/notes";
    }


    @GetMapping("/notes/{id}")
    public String fullNote(@PathVariable(value = "id") long id, Model model) {
        if (notesService.existsById(id)) {
            Optional<Note> notes = notesService.findById(id);
            ArrayList<Note> res = new ArrayList<>();
            notes.ifPresent(res::add);
            model.addAttribute("note", res);
            List<String> listOfTags = SearchFromRepo.parseTagsAsList(notesService.getTagsById(id));
            listOfTags.replaceAll(s -> s.trim());
            model.addAttribute("tags", listOfTags);
            Iterable<Comment> comments = commentRepository.reverseFindById(id);
            model.addAttribute("comments", comments);
            return "note/noteDetails";
        } else return "redirect:/notes";
    }

    @GetMapping("/notes/search/{tag}")
    public String searchTag(@PathVariable(value = "tag") String tag, Model model) {
        Iterable<Note> notes = notesService.reverseFindByTag(tag);
        model.addAttribute("notes", notes);
        return "note/mainNotes";
    }

    @PostMapping("/notes/{id}")
    public String addComment(@PathVariable(value = "id") long id, @RequestParam String fullComment, Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Comment comment = new Comment(id, auth.getName(), fullComment, CurrDate.getCurrDate());
        commentRepository.save(comment);
        Optional<Note> notes = notesService.findById(id);
        ArrayList<Note> res = new ArrayList<>();
        notes.ifPresent(res::add);
        model.addAttribute("note", res);
        Iterable<Comment> comments = commentRepository.reverseFindById(id);
        model.addAttribute("comments", comments);
        return "note/noteDetails";
    }

    @GetMapping("/notes/edit/{id}")
    public String editNote(@PathVariable(value = "id") long id, Model model) {
        if (notesService.existsById(id)) {
            Optional<Note> notes = notesService.findById(id);
            ArrayList<Note> res = new ArrayList<>();
            notes.ifPresent(res::add);
            model.addAttribute("note", res);
            return "note/editNote";
        } else return "redirect:/notes";
    }

    @PostMapping("/notes/edit/{id}")
    public String updateNote(@PathVariable(value = "id") long id, @RequestParam String heading, @RequestParam String fullText, @RequestParam String tags, Model model) {
        if (notesService.existsById(id)) {
            Note note = notesService.findById(id).orElseThrow();
            note.setHeading(heading);
            note.setFinalFullText(note.toHtmlBreakLines(fullText));
            note.setTags(tags);
            note.setDate(CurrDate.getCurrDate());
            notesService.saveNote(note);
            return "redirect:/notes/{id}";
        } else return "redirect:/notes";
    }

    @GetMapping("/notes/delete/{id}")
    public String deleteNote(@PathVariable(value = "id") long id, Model model) {
        if (notesService.existsById(id)) {
            notesService.delete(notesService.findById(id).orElseThrow());
            return "redirect:/notes";
        } else return "redirect:/notes";
    }
}
