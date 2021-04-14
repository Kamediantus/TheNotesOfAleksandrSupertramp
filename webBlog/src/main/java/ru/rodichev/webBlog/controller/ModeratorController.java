package ru.rodichev.webBlog.controller;


import org.aspectj.weaver.ast.Not;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.annotation.AccessType;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.rodichev.webBlog.entity.Note;
import ru.rodichev.webBlog.logic.Remark;
import ru.rodichev.webBlog.repo.NotesRepository;

import java.util.ArrayList;
import java.util.List;

@Controller
public class ModeratorController {

    @Autowired
    private NotesRepository notesRepository;

    @GetMapping("/moderator")
    public String moderatorPage(Model model){
        Iterable<Note> notes = notesRepository.getOnlyUnchecked();
        model.addAttribute("notes", notes);
        return "moderator/main";
    }

    @GetMapping("/moderate/{id}")
    public String moderDetails(@PathVariable("id") Long id, Model model){
        Note note = notesRepository.getById(id);
        if (!note.isChecked()){
            model.addAttribute("note", note);
        } else model.addAttribute("msg", "this note already checked");
        return "moderator/details";
    }

    @PostMapping("/moderate/{id}")
    public String uploadRemarks(@PathVariable("id") Long id,@RequestParam String allRemarks, Model model){
        Note note = notesRepository.getById(id);
        note.setModerateFullText(allRemarks);
        note.setChecked(true);
        notesRepository.save(note);


        model.addAttribute("viewRemarks", Remark.getPopupText(note.getRawFullText(), Remark.getSortRemarks(allRemarks)));


        return "moderator/test";
    }

}